package com.spyrdonapps.weightreductor.backend.util.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

object JWTUtils {

    private const val ACCESS_TOKEN_VALIDITY_MINUTES: Long = 10
    private const val REFRESH_TOKEN_VALIDITY_MINUTES: Long = 30
    private const val tokenTypeKey = "token_type"
    private const val jwtSecret = "ghq384qgh8opq48hgqopgh" // todo secure this
    private val algorithm = Algorithm.HMAC256(jwtSecret)

    val verifier: JWTVerifier = JWT.require(algorithm).build()

    @OptIn(ExperimentalTime::class)
    fun createTokenData(userId: UserId): TokenData {
        val accessToken = JWT.create()
            .withSubject(userId.toString())
            .withExpiresAt(
                Date.from((Clock.System.now() + Duration.minutes(ACCESS_TOKEN_VALIDITY_MINUTES)).toJavaInstant())
            )
            .withClaim(tokenTypeKey, TokenType.Access.name)
            .sign(algorithm)
        val refreshToken = JWT.create()
            .withSubject(userId.toString())
            .withExpiresAt(
                Date.from((Clock.System.now() + Duration.minutes(REFRESH_TOKEN_VALIDITY_MINUTES)).toJavaInstant())
            )
            .withClaim(tokenTypeKey, TokenType.Refresh.name)
            .sign(algorithm)
        return TokenData(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun getTokenType(payload: Payload): TokenType {
        val rawTokenType = payload.claims[tokenTypeKey]?.asString()
            ?: httpStatusException(HttpStatusCode.BadRequest, "No token type")
        @Suppress("UnnecessaryVariable") // for debugging breakpoint
        val tokenType = TokenType.valueOf(rawTokenType)
        return tokenType
    }
}