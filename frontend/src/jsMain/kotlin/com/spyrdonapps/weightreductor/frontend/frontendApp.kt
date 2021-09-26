package com.spyrdonapps.weightreductor.frontend

import androidx.compose.runtime.*
import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.clientType
import com.spyrdonapps.common.repository.ClientRepository
import com.spyrdonapps.weightreductor.JsBuildConfig
import com.spyrdonapps.weightreductor.frontend.components.LoginForm
import kotlinext.js.require
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

lateinit var repository: ClientRepository
lateinit var logger: Kermit

fun main() {
    val environment = Environment.fromRawEnvironment(JsBuildConfig.RAW_ENVIRONMENT)
    environment.ensureAvailableForClientType(clientType = clientType)
    val koin = initKoin(environment = environment).koin
    repository = koin.get()
    logger = koin.get()
    require("./styles.css")
    renderComposable(rootElementId = "root") {
        val isLoggedIn by repository.isLoggedInStateFlow.collectAsState()
        if (isLoggedIn) {
            LoggedInContent()
        } else {
            NotLoggedInContent()
        }
    }
}

@Composable
fun NotLoggedInContent() {
    val scope = rememberCoroutineScope()
    LoginForm(
        onLoginButtonClick = { userCredentials ->
            logger.d { "Login button clicked, credentials: $userCredentials" }
            scope.launch {
                try {
                    repository.login(userCredentials)
                    logger.d { "Logged in" }
                } catch (e: Exception) {
                    logger.e(e) { "Error logging in" }
                }
            }
        }
    )
}

@Composable
fun LoggedInContent() {
    Text("You are logged in")
}