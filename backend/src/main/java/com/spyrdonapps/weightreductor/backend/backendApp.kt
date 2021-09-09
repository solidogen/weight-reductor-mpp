@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import com.spyrdonapps.weightreductor.backend.database.DatabaseSettings
import com.spyrdonapps.weightreductor.backend.di.backendModule
import com.spyrdonapps.weightreductor.backend.repository.UsersRepository
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import com.spyrdonapps.weightreductor.backend.routing.auth
import com.spyrdonapps.weightreductor.backend.routing.weighings
import com.spyrdonapps.weightreductor.backend.util.utils.*
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
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
            if (cause is HttpStatusException) {
                log.error("${cause.httpStatusCode} ${cause.message}")
                call.respond(cause.httpStatusCode, cause.message)
            } else {
                log.error("Internal error: ${cause.stackTraceToString()}")
                call.respond(HttpStatusCode.InternalServerError, cause.message.orEmpty())
            }
        }
    }

    DatabaseSettings.init(appRunMode)

    install(FlywayFeature) {
        dataSource = DatabaseSettings.dataSource
        locations = arrayOf("classpath:db/migration")
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
    install(Authentication) {
        jwt {
            verifier(JWTUtils.verifier)
            validate { credentials ->
                val tokenType = JWTUtils.getTokenType(credentials.payload)
                when (tokenType) {
                    TokenType.Access -> JWTPrincipal(credentials.payload)
                    TokenType.Refresh -> httpStatusException(HttpStatusCode.Forbidden, "Wrong token type")
                }.exhaustive
            }
        }
    }

    val weighingsRepository: WeighingsRepository by inject()
    val usersRepository: UsersRepository by inject()
    val requestValidator: RequestValidator by inject()

    routing {
        fun allRoutes(root: Route): List<Route> {
            return listOf(root) + root.children.flatMap { allRoutes(it) }
        }
        get("/") {
            call.respond("API alive")
        }
        get("/routes") {
            val root = feature(Routing)
            val allRoutes = allRoutes(root)
            val allRoutesWithMethod = allRoutes.filter { it.selector is HttpMethodRouteSelector }
            call.respond(allRoutesWithMethod.joinToString("\n"))
        }
        weighings(weighingsRepository)
        auth(usersRepository, requestValidator)
    }

    if (!DatabaseSettings.hasJdbcUrlSet) {
        GlobalScope.launch {
            weighingsRepository.upsert(Weighing(85f, LocalDate.parse("2020-02-02").atStartOfDayIn(TimeZone.UTC)))
        }
    }
}