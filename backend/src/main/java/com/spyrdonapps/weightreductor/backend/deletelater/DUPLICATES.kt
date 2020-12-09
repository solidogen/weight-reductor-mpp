package com.spyrdonapps.weightreductor.backend.deletelater

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItem(val id: Int, val name: String, val priority: Int)