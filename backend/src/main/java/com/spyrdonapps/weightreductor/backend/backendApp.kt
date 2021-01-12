package com.spyrdonapps.weightreductor.backend

import co.touchlab.kermit.Kermit
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
        watchPaths = listOf("backend")
    ).start(wait = true)
}

fun Application.appModule() {
    install(DefaultHeaders)
    install(ContentNegotiation) { json() }
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        anyHost()
    }
    install(Compression) { gzip() }
    install(CallLogging) {
        level = Level.DEBUG
    }
    install(Locations)
    install(Koin) { modules(backendModule) }
    install(StatusPages) {
        exception<Throwable> { cause ->
            log.error("Internal error", cause)
            call.respond(HttpStatusCode.InternalServerError, cause.toString())
        }
    }

    val weighingsRepository: WeighingsRepository by inject()

    routing {
        weighings(weighingsRepository)
    }
}