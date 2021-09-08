@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend.routing

import com.spyrdonapps.common.model.*
import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.weightreductor.backend.repository.UsersRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.auth(usersRepository: UsersRepository) {
    post(ApiEndpoints.login) {
        // todo request object
        val userCredentials: UserCredentials = call.receive()
        if (usersRepository.checkCredentials(userCredentials)) {
//            val tokenData todo
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Forbidden, "Wrong credentials")
        }
    }
    post(ApiEndpoints.register) {
        // todo request object
        val userCredentials: UserCredentials = call.receive()
        usersRepository.register(userCredentials)
        // handle conflict?
        //  val tokenData todo
        call.respond(HttpStatusCode.OK)
    }
}