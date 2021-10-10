package com.spyrdonapps.weightreductor.frontend

import androidx.compose.runtime.*
import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.clientType
import com.spyrdonapps.common.repository.ClientRepository
import com.spyrdonapps.weightreductor.JsBuildConfig
import com.spyrdonapps.weightreductor.frontend.components.AppHeader
import com.spyrdonapps.weightreductor.frontend.components.LoginForm
import com.spyrdonapps.weightreductor.frontend.utils.DefaultNavigator
import com.spyrdonapps.weightreductor.frontend.utils.Navigator
import com.spyrdonapps.weightreductor.frontend.utils.SitePage
import kotlinext.js.require
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

lateinit var repository: ClientRepository
lateinit var logger: Kermit
lateinit var coroutineScope: CoroutineScope

private val navigator: Navigator = DefaultNavigator()

val isLoggedInStateFlow: StateFlow<Boolean> by lazy {
    repository.isLoggedInStateFlow
}

@OptIn(ExperimentalCoroutinesApi::class)
val navigationStateFlow: StateFlow<SitePage> by lazy {
    combine(
        navigator.mainPagesStateFlow,
        isLoggedInStateFlow
    ) { mainSitePage, isLoggedIn ->
        if (isLoggedIn) {
            mainSitePage
        } else {
            SitePage.Login
        }
    }
        .onEach { sitePage -> logger.d { "Current site page: $sitePage, is logged in: ${isLoggedInStateFlow.value}" } }
        .stateIn(coroutineScope, SharingStarted.Eagerly, SitePage.Home)
}

fun main() {
    val environment = Environment.fromRawEnvironment(JsBuildConfig.RAW_ENVIRONMENT)
    environment.ensureAvailableForClientType(clientType = clientType)
    val koin = initKoin(environment = environment).koin
    repository = koin.get()
    logger = koin.get()
    coroutineScope = koin.get()
    val onLogoutButtonClick = { repository.logout() }
    logger.d { "Environment: $environment" }
    require("./styles.css")
    renderComposable(rootElementId = "root") {
        val isLoggedIn by isLoggedInStateFlow.collectAsState()
        val sitePage by navigationStateFlow.collectAsState()
        AppHeader(isLoggedIn, navigator, onLogoutButtonClick)
        AppMain(isLoggedIn, sitePage)
        AppFooter()
    }
}

@Composable
fun AppMain(isLoggedIn: Boolean, sitePage: SitePage) {
    if (isLoggedIn) {
        LoggedInContent(sitePage)
    } else {
        NotLoggedInContent()
    }
}

@Composable
fun AppFooter() {

}

@Composable
fun NotLoggedInContent() {
    val scope = rememberCoroutineScope()
    LoginForm(
        onLoginButtonClick = { userCredentials ->
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
fun LoggedInContent(sitePage: SitePage) {
    Text("You are logged in")
    Text("Current page: $sitePage")
}