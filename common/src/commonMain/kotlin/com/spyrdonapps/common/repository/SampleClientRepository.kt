package com.spyrdonapps.common.repository

import com.spyrdonapps.common.model.ShoppingListItem
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class SampleClientRepository {

    private val baseUrl = "http://$localhostDomain:9090/"

    private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }

    private val client by lazy {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(nonStrictJson)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }

    suspend fun fetchHome() = client.get<List<ShoppingListItem>>(baseUrl)
}