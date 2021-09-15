@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend.util.utils

import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import io.ktor.http.*

class RequestValidator {

    fun validateRegisterOrThrow(userCredentials: UserCredentials) {
        val username = userCredentials.username
        val password = userCredentials.password
        if (username.length < 2) httpStatusException(HttpStatusCode.BadRequest, "Username too short")
        if (password.length < 5) httpStatusException(HttpStatusCode.BadRequest, "Password too short")
    }
}