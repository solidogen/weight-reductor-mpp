package com.spyrdonapps.weightreductor.android.ui.custom

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spyrdonapps.weightreductor.android.ui.features.home.HomeScreen
import com.spyrdonapps.weightreductor.android.ui.features.login.LoginScreen
import com.spyrdonapps.weightreductor.android.ui.features.main.MainViewModel
import com.spyrdonapps.weightreductor.android.ui.features.onboarding.OnboardingScreen
import com.spyrdonapps.weightreductor.android.ui.features.register.RegisterScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

object AppDestinations {
    const val ONBOARDING_ROUTE = "onboarding"
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val HOME_ROUTE = "home"
}

class AppActions(navController: NavHostController) {
    val goToLoginScreen: () -> Unit = {
        navController.navigate(AppDestinations.LOGIN_ROUTE)
    }
    val goToRegisterScreen: () -> Unit = {
        navController.navigate(AppDestinations.REGISTER_ROUTE)
    }
    val goToHomeScreen: () -> Unit = {
        navController.navigate(AppDestinations.HOME_ROUTE)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}

@Composable
fun AppNavGraph(startDestination: String = AppDestinations.ONBOARDING_ROUTE) {
    val navController = rememberNavController()
    val viewModel: MainViewModel = getViewModel()
    val actions = remember(navController) { AppActions(navController) }
    val scaffoldState = rememberScaffoldState()
    val errorState by viewModel.errorLiveData.observeAsState()
    if (errorState?.hasBeenHandled == false) {
        LaunchedEffect(key1 = errorState) {
            errorState?.getContentIfNotHandled()?.let {
                scaffoldState.snackbarHostState.showSnackbar(message = it)
            }
        }
    }
    val tokenDataState by viewModel.tokenDataLiveData.observeAsState()
    if (tokenDataState != null) {
        LaunchedEffect(key1 = tokenDataState) {
            actions.goToHomeScreen.invoke()
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(AppDestinations.ONBOARDING_ROUTE) {
                OnboardingScreen(goToLoginScreen = actions.goToLoginScreen)
            }
            composable(AppDestinations.LOGIN_ROUTE) {
                LoginScreen(goToRegisterScreen = actions.goToRegisterScreen, loginRequested = viewModel::loginRequested)
            }
            composable(AppDestinations.REGISTER_ROUTE) {
                RegisterScreen(goToLoginScreen = actions.goToLoginScreen, registerRequested = viewModel::registerRequested)
            }
            composable(AppDestinations.HOME_ROUTE) {
                HomeScreen(goToSettings = {  })
            }
        }
    }
}
