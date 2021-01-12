package com.spyrdonapps.common.repository

import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.model.Weighing
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class WeighingRepository(
    private val client: HttpClient,
    private val environment: Environment
) {
    // todo route to common code from BE
    // todo use ApiResponse/ApiResult wrapper

    suspend fun getAllWeighings() = client.get<List<Weighing>>("${environment.baseUrl}/weighing")

    suspend fun postWeighing(weighing: Weighing) = client.post<Unit> {
        url("${environment.baseUrl}/weighing")
        contentType(ContentType.Application.Json)
        body = weighing
    }
    // todo delete etc
}