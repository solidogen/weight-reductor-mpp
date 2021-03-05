package com.spyrdonapps.weightreductor.backend

import com.spyrdonapps.weightreductor.backend.database.DatabaseSettings
import com.spyrdonapps.weightreductor.backend.di.backendModule
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import com.spyrdonapps.weightreductor.backend.routing.weighings
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger
import org.slf4j.event.Level

/**
 * Not able to use DI or any class in common module here (AGP doesn't work well with multiplatform).
 * Any common data classes will be temporarily duplicated in 2 files named [DUPLICATES] in backend module AND common module.
 * */
fun main() {
    embeddedServer(
        Netty,
        port = System.getenv("PORT")?.toInt() ?: 9090,
        module = Application::appModule,
        watchPaths = listOf("backend") // fixme - this doesn't recompile BE
    ).start(wait = true)
}

fun Application.appModule() {
    install(DefaultHeaders)
    install(ContentNegotiation) { json() }
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
    install(Compression) { gzip() }
    install(CallLogging) {
        level = Level.DEBUG
    }
    install(Locations)
    install(Koin) {
        modules(backendModule)
        SLF4JLogger()
    }
    install(StatusPages) {
        exception<Throwable> { cause ->
            log.error("Internal error", cause)
            call.respond(HttpStatusCode.InternalServerError, cause.toString())
        }
    }

    val weighingsRepository: WeighingsRepository by inject()

    routing {
        get("/") {
            call.respond("API alive")
        }
        weighings(weighingsRepository)
    }

    DatabaseSettings.init()
}