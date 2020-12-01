package com.spyrdonapps.weightreductor.frontend

import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.repository.SampleClientRepository
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
}