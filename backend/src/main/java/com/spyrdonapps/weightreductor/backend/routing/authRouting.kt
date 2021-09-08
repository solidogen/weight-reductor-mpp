package com.spyrdonapps.weightreductor.backend.routing

import com.spyrdonapps.common.model.*
import com.spyrdonapps.common.devonly.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

var users = mutableListOf<UserCredentials>().apply {
    add(UserCredentials("user1", "ooooo"))
}

fun Route.auth() {
    post(ApiEndpoints.login) {
        // todo request object
        val userCredentials: UserCredentials = call.receive()
        if (users.contains(userCredentials)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Forbidden, "Wrong credentials")
        }
    }
    post(ApiEndpoints.register) {
        // todo request object
        val userCredentials: UserCredentials = call.receive()
        if (users.map { it.username }.contains(userCredentials.username)) {
            call.respond(HttpStatusCode.Conflict, "Username already exists")
        } else {
            users.add(userCredentials)
            call.respond(HttpStatusCode.OK)
        }
    }
}