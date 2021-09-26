package com.spyrdonapps.common.repository

import com.spyrdonapps.common.model.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class ClientRepository(
    private val client: HttpClient,
    private val environment: Environment,
    private val globalScope: CoroutineScope
) {
    private val baseUrl = environment.baseUrl
    // todo use ApiResponse/ApiResult wrapper (flow usecase)

    private var tokenDataStateFlow: MutableStateFlow<TokenData?> = MutableStateFlow(null)
    private var tokenData: TokenData?
        get() = tokenDataStateFlow.value
        set(value) { tokenDataStateFlow.value = value }

    val isLoggedInStateFlow: StateFlow<Boolean> = tokenDataStateFlow.map { tokenData != null }.stateIn(
        scope = globalScope,
        started = SharingStarted.Eagerly,
        initialValue = false // todo load from cache? multiplatform settings (but is it secure?)
    )

    suspend fun getAllWeighings(): List<Weighing> =
        client.get("$baseUrl${ApiEndpoints.weighings}")

    suspend fun postWeighing(weighing: Weighing) = client.post<Unit> {
        addBearerHeader(tokenData)
        url("$baseUrl${ApiEndpoints.weighingsAdd}")
        contentType(ContentType.Application.Json)
        body = weighing
    }

    suspend fun getWeighingById(id: Long): Weighing =
        client.get("$baseUrl${ApiEndpoints.weighings}/$id")

    suspend fun login(userCredentials: UserCredentials): TokenData {
        return client.post<TokenData> {
            url("$baseUrl${ApiEndpoints.login}")
            contentType(ContentType.Application.Json)
            body = userCredentials
        }.also {
            tokenData = it
        }
    }

    suspend fun register(userCredentials: UserCredentials): TokenData {
        return client.post<TokenData> {
            url("$baseUrl${ApiEndpoints.register}")
            contentType(ContentType.Application.Json)
            body = userCredentials
        }.also {
            tokenData = it
        }
    }

    // todo use this
    suspend fun refreshToken(request: RefreshTokenRequest): TokenData {
        return client.post<TokenData> {
            url("$baseUrl${ApiEndpoints.refreshToken}")
            contentType(ContentType.Application.Json)
            body = request
        }.also {
            tokenData = it
        }
    }

    private fun HttpRequestBuilder.addBearerHeader(tokenData: TokenData?) {
        header("Authorization", "Bearer ${tokenData?.accessToken.orEmpty()}")
    }
}