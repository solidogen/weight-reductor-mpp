package com.spyrdonapps.common.repository

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ShoppingListItem(val id: Int, val name: String, val priority: Int)

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