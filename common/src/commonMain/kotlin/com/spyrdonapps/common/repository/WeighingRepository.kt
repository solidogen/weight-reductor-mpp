package com.spyrdonapps.common.repository

import com.spyrdonapps.common.model.Weighing
import io.ktor.client.*
import io.ktor.client.request.*

class WeighingRepository(
    private val client: HttpClient,
    private val baseUrl: String = "https://dev-wr.herokuapp.com"
) {
    // todo route to common code from BE
    suspend fun getAllWeighings() = client.get<List<Weighing>>("$baseUrl/weighing")

    // todo post/delete
}