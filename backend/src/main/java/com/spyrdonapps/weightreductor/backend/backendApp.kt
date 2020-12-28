package com.spyrdonapps.weightreductor.backend

import com.mongodb.ConnectionString
import com.spyrdonapps.weightreductor.backend.deletelater.ShoppingListItem
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import kotlin.random.Random

val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
    ConnectionString("$it?retryWrites=false&w=majority")
}

val client = if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient().coroutine
val database = client.getDatabase(connectionString?.database ?: "test")
val collection = database.getCollection<ShoppingListItem>()

/**
 * Not able to use DI or any class in common module here (AGP doesn't work well with multiplatform).
 * Any common data classes will be temporarily duplicated in [DUPLICATES] file.
 * */
fun main() {
    embeddedServer(Netty, System.getenv("PORT")?.toInt() ?: 9090) {
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
                call.respond("Use /shopping")
            }
            get("/add") {
                val randomInt = Random.nextInt()
                collection.insertOne(ShoppingListItem(randomInt, "ASD_$randomInt", 1))
                call.respond("Added")
            }
            route("/shopping") {
                get {
                    call.respond(collection.find().toList())
                }
                post {
                    collection.insertOne(call.receive<ShoppingListItem>())
                    call.respond(HttpStatusCode.OK)
                }
                delete("/{id}") {
                    val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
                    collection.deleteOne(ShoppingListItem::id eq id)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }.start(wait = true)
}