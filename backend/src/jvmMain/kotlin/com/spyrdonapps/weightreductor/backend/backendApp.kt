package com.spyrdonapps.weightreductor.backend

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.Weighing
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
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger
import org.slf4j.event.Level
import java.net.HttpURLConnection
import java.net.URL
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

lateinit var kermit: Kermit
    private set

lateinit var server: NettyApplicationEngine
    private set

fun main() {
    embeddedServer(
        Netty,
        port = System.getenv("PORT")?.toInt() ?: 9090,
        module = {
            appModule(AppRunMode.Default)
        },
        watchPaths = listOf("com.spyrdonapps.weightreductor.backend") // todo - this doesn't recompile BE
    ).also {
        server = it
    }.start(wait = true).also {
        it.addShutdownHook {
            kermit.w { "SERVER SHUTDOWN" }
            DatabaseSettings.h2server?.stop()
        }
    }
}

@OptIn(KtorExperimentalAPI::class)
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
            kermit = koin.get()
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
    val globalScope: CoroutineScope by inject()

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
        globalScope.launch {
            weighingsRepository.upsert(Weighing(85f, LocalDate.parse("2020-02-02").atStartOfDayIn(TimeZone.UTC)))
        }
    }

    keepServerFromSleeping(globalScope)
}

@OptIn(ExperimentalTime::class)
private fun keepServerFromSleeping(globalScope: CoroutineScope) {
    globalScope.launch {
        while (true) {
            delay(Duration.minutes((12..14).random()))
            val connector = server.environment.connectors.first()
            val protocol = connector.type.name.lowercase()
            val thisServerUrl = "$protocol://${connector.host}:${connector.port}"
            getUrl(thisServerUrl)
        }
    }
}

private fun getUrl(rawUrl: String) {
    try {
        val url = URL(rawUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.doOutput = true
        val responseCode = connection.responseCode
        kermit.d { "Response code of $rawUrl: $responseCode" }
    } catch (e: Exception) {
        kermit.w { e.message.orEmpty() }
    }
}