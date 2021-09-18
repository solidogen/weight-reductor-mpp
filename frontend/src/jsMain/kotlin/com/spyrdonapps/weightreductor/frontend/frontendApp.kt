package com.spyrdonapps.weightreductor.frontend

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.clientType
import com.spyrdonapps.common.repository.ClientRepository
import com.spyrdonapps.weightreductor.JsBuildConfig
import com.spyrdonapps.weightreductor.frontend.components.LoginForm
import kotlinext.js.require
import org.jetbrains.compose.web.renderComposable


fun main() {
    val environment = Environment.fromRawEnvironment(JsBuildConfig.RAW_ENVIRONMENT)
    environment.ensureAvailableForClientType(clientType = clientType)
    val koin = initKoin(environment = environment).koin
    val repository: ClientRepository = koin.get()
    val logger: Kermit = koin.get()
    require("./styles.css")
    renderComposable(rootElementId = "root") {
        LoginForm()
    }
}