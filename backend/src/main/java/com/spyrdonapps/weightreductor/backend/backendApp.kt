package com.spyrdonapps.weightreductor.backend

import com.spyrdonapps.weightreductor.backend.deletelater.ShoppingListItem
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

/**
 * Not able to use DI or any class in common module here (AGP doesn't work well with multiplatform).
 * Any common data classes will be temporarily duplicated in [DUPLICATES] file.
 * */
fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        routing {
            get("/") {
                call.respond(shoppingList)
            }
        }
    }.start(wait = true)
}

private val shoppingList = mutableListOf(
    ShoppingListItem(0, "Cucumbers 🥒", 1),
    ShoppingListItem(1, "Tomatoes 🍅", 2),
    ShoppingListItem(2, "Orange Juice 🍊", 3)
)