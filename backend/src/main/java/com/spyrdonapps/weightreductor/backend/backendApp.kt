package com.spyrdonapps.weightreductor.backend

import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import com.spyrdonapps.weightreductor.backend.database.DatabaseSettings
import com.spyrdonapps.weightreductor.backend.di.backendModule
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import com.spyrdonapps.weightreductor.backend.routing.auth
import com.spyrdonapps.weightreductor.backend.routing.weighings
import com.spyrdonapps.weightreductor.backend.util.utils.AppRunMode
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger
import org.slf4j.event.Level

fun main() {
    embeddedServer(
        Netty,
        port = System.getenv("PORT")?.toInt() ?: 9090,
        module = {
            appModule(AppRunMode.Default)
        },
        watchPaths = listOf("com.spyrdonapps.weightreductor.backend") // todo - this doesn't recompile BE
    ).start(wait = true)
}

const val BASIC_AUTH_KEY = "BASIC_AUTH_KEY"

fun Application.appModule(appRunMode: AppRunMode) {
    install(DefaultHeaders)
    install(Locations)
    install(ContentNegotiation) {
        json()
    }
    install(Compression) {
        gzip()
    }
    install(CallLogging) {
        level = Level.DEBUG
    }
    /**
    * This way I can override/mock dependencies within unit tests as it's not possible to startKoin twice
    * */
    if (appRunMode != AppRunMode.UnitTesting) {
        install(Koin) {
            modules(backendModule)
            SLF4JLogger()
        }
    }
    install(StatusPages) {
        exception<Throwable> { cause ->
            log.error("Internal error", cause)
            call.respond(HttpStatusCode.InternalServerError, cause.toString())
        }
    }

    DatabaseSettings.init(appRunMode)

    install(FlywayFeature) {
        dataSource = DatabaseSettings.dataSource
        locations = arrayOf("classpath:db/migration")
    }

    install(Authentication) {
        basic(BASIC_AUTH_KEY) {
            validate { credentials ->
                if (credentials.name == "admin" && credentials.password == "admin") {
                    return@validate UserIdPrincipal("admin")
                } else {
                    null
                }
            }
        }
    }
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.Accept)
        header(HttpHeaders.AcceptLanguage)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.Authorization)
        anyHost()
    }

    val weighingsRepository: WeighingsRepository by inject()

    routing {
        get("/") {
            call.respond("API alive")
        }
        authenticate(BASIC_AUTH_KEY) {
            get("/login") {
                val user =
                    call.authentication.principal as? UserIdPrincipal ?: error("No principal")
                call.respond("Now you are logged in, ${user.name}")
            }
        }
        weighings(weighingsRepository)
        auth()
    }

    if (!DatabaseSettings.hasJdbcUrlSet) {
        GlobalScope.launch {
            weighingsRepository.upsert(Weighing(85f, LocalDate.parse("2020-02-02").atStartOfDayIn(TimeZone.UTC)))
        }
    }
}