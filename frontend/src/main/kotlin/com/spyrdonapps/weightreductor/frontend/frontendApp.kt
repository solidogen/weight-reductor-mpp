package com.spyrdonapps.weightreductor.frontend

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.repository.SampleClientRepository
import com.spyrdonapps.weightreductor.frontend.components.appMainComponent
import com.spyrdonapps.weightreductor.frontend.utils.normalizeCss
import org.koin.core.KoinComponent
import org.koin.core.get
import react.createContext
import react.dom.render

object AppDependencies : KoinComponent {
    val repository: SampleClientRepository
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