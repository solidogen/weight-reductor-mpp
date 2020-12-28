package com.spyrdonapps.common.repository

import com.spyrdonapps.common.model.ShoppingListItem
import io.ktor.client.*
import io.ktor.client.request.*

class SampleClientRepository(
    private val client: HttpClient,
    private val baseUrl: String = "https://dev-wr.herokuapp.com"
) {
    suspend fun fetchHome() = client.get<List<ShoppingListItem>>("$baseUrl/shopping")

    // todo post/delete
}