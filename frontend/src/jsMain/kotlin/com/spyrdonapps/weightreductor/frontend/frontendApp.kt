package com.spyrdonapps.weightreductor.frontend

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.clientType
import com.spyrdonapps.common.repository.ClientRepository
import com.spyrdonapps.weightreductor.JsBuildConfig
import kotlinext.js.require
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun String.varargClasses(): Array<String> = split(" ").toTypedArray()

fun main() {
    val environment = Environment.fromRawEnvironment(JsBuildConfig.RAW_ENVIRONMENT)
    environment.ensureAvailableForClientType(clientType = clientType)
    val koin = initKoin(environment = environment).koin
    val repository: ClientRepository = koin.get()
    val logger: Kermit = koin.get()
    require("./styles.css")
    renderComposable(rootElementId = "root") {
        Div(attrs = { classes(*("bg-green-300 border-green-600 border-b p-4 m-4 rounded").varargClasses()) }) {
            Text("hello")
        }
    }
}