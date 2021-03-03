package com.spyrdonapps.weightreductor.frontend

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.repository.WeighingRepository
import com.spyrdonapps.weightreductor.frontend.components.appMainComponent
import com.spyrdonapps.weightreductor.frontend.utils.normalizeCss
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import react.createContext
import react.dom.render

object AppDependencies : KoinComponent {
    val repository: WeighingRepository
    val logger: Kermit

    init {
        initKoin()
        repository = get()
        logger = get()
    }
}

val AppDependenciesContext = createContext<AppDependencies>()

fun main() {
    render(kotlinx.browser.document.getElementById("root")) {
        normalizeCss()
        AppDependenciesContext.Provider(AppDependencies) {
            appMainComponent { }
        }
    }
}