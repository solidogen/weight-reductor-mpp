package com.spyrdonapps.common.repository

import com.spyrdonapps.common.model.ApiEndpoints
import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.model.Weighing
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class WeighingRepository(
    private val client: HttpClient,
    private val environment: Environment
) {
    private val baseUrl = environment.baseUrl
    // todo use ApiResponse/ApiResult wrapper (flow usecase)

    suspend fun getAllWeighings(): List<Weighing> =
        client.get("$baseUrl/${ApiEndpoints.weighings}")

    suspend fun postWeighing(weighing: Weighing) = client.post<Unit> {
        url("$baseUrl/${ApiEndpoints.weighingsAdd}")
        contentType(ContentType.Application.Json)
        body = weighing
    }

    suspend fun getWeighingById(id: Long): Weighing =
        client.get("$baseUrl/${ApiEndpoints.weighings}/$id")
}