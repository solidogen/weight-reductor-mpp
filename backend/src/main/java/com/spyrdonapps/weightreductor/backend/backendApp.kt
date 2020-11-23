package com.spyrdonapps.weightreductor.backend

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, 9090) {
        routing {
            get("/") {
                call.respond("XD")
            }
        }
    }.start(wait = true)
}