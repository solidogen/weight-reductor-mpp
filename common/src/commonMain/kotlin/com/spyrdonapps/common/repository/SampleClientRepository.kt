package com.spyrdonapps.common.repository

import com.spyrdonapps.common.model.ShoppingListItem
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class SampleClientRepository(
    private val client: HttpClient,
    private val baseUrl: String = "http://$localhostDomain:9090/"
) {
    suspend fun fetchHome() = client.get<List<ShoppingListItem>>(baseUrl)
}