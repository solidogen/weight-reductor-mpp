package com.spyrdonapps.weightreductor.frontend

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.repository.SampleClientRepository
import org.koin.core.KoinComponent
import org.koin.core.get
import react.child
import react.createContext
import react.dom.render

object AppDependencies : KoinComponent {
    val repository: SampleClientRepository
    val logger: Kermit

    init {
        initKoin()
        repository = get()
        logger = get()
        logger.d { "initalized appdep" }
    }
}

val AppDependenciesContext = createContext<AppDependencies>()

fun main() {
    render(kotlinx.browser.document.getElementById("root")) {
        AppDependenciesContext.Provider(AppDependencies) {
            child(FrontendAppComponent)
        }
    }
    AppDependencies.logger.d { "initalized main" }
}