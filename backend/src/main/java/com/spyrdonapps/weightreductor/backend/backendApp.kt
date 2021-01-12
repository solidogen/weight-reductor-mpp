package com.spyrdonapps.weightreductor.backend

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

/**
 * Not able to use DI or any class in common module here (AGP doesn't work well with multiplatform).
 * Any common data classes will be temporarily duplicated in [DUPLICATES] file.
 * */
fun main() {
    embeddedServer(Netty, System.getenv("PORT")?.toInt() ?: 9090) {
        install(DefaultHeaders)
        install(ContentNegotiation) { json() }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) { gzip() }
        install(CallLogging)
        install(Locations)
        install(Koin) { modules(backendModule) }
        install(StatusPages)

        val weighingsRepository: WeighingsRepository by inject()

        routing {
            weighings(weighingsRepository)
        }
    }.start(wait = true)
}