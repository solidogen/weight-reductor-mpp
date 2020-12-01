package com.spyrdonapps.weightreductor.frontend

import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.model.ShoppingListItem
import com.spyrdonapps.common.repository.SampleClientRepository
import com.spyrdonapps.common.repository.getLogger
import react.child
import react.createContext
import react.dom.render

object AppDependencies {
    val repository = SampleClientRepository()
}

val AppDependenciesContext = createContext<AppDependencies>()

fun main() {
    initKoin()
    render(kotlinx.browser.document.getElementById("root")) {
        AppDependenciesContext.Provider(AppDependencies) {
            child(FrontendAppComponent)
        }
    }
    getLogger().d("initalized", "")
}

// todo remove. this resolves correctly
private val shoppingList = mutableListOf(
    ShoppingListItem(0, "Cucumbers 🥒", 1),
    ShoppingListItem(1, "Tomatoes 🍅", 2),
    ShoppingListItem(2, "Orange Juice 🍊", 3)
)