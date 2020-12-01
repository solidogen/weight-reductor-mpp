package com.spyrdonapps.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItem(val id: Int, val name: String, val priority: Int)