@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend.routing

import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import com.spyrdonapps.weightreductor.backend.repository.UsersRepository
import com.spyrdonapps.weightreductor.backend.util.utils.JWTUtils
import com.spyrdonapps.weightreductor.backend.util.utils.RequestValidator
import com.spyrdonapps.weightreductor.backend.util.utils.TokenType
import com.spyrdonapps.weightreductor.backend.util.utils.httpStatusException
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.auth(usersRepository: UsersRepository, requestValidator: RequestValidator) {
    post(ApiEndpoints.login) {
        val userCredentials: UserCredentials = call.receive()
        val userId = usersRepository.checkCredentials(userCredentials)
        val tokenData = JWTUtils.createTokenData(userId = userId)
        call.respond(HttpStatusCode.OK, tokenData)
    }
    post(ApiEndpoints.register) {
        val userCredentials: UserCredentials = call.receive()
        requestValidator.validateRegisterOrThrow(userCredentials)
        val userId = usersRepository.register(userCredentials)
        val tokenData = JWTUtils.createTokenData(userId = userId)
        call.respond(HttpStatusCode.Created, tokenData)
    }
    post(ApiEndpoints.refreshToken) {
        val requestTokenRequest: RefreshTokenRequest = call.receive()
        val sentRefreshToken = requestTokenRequest.refreshToken
        val verifier = JWTUtils.verifier
        val decodedJwt = verifier.verify(sentRefreshToken)
        val tokenType = JWTUtils.getTokenType(decodedJwt)
        if (tokenType != TokenType.Refresh) {
            httpStatusException(HttpStatusCode.Forbidden, "Wrong token type")
        }
        val userId = decodedJwt.subject.toLong()
        // todo check if sent refresh token is revoked
        // todo revoke sent refresh token now before sending out a new one
        val tokenData = JWTUtils.createTokenData(userId)
        call.respond(tokenData)
    }
    // todo - follow https://github.com/ktorio/ktor-documentation/blob/main/codeSnippets/snippets/auth-jwt-hs256/src/main/kotlin/com/example/Application.kt
}