package com.spyrdonapps.weightreductor.android.ui.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.spyrdonapps.weightreductor.android.ui.features.home.HomeScreen
import com.spyrdonapps.weightreductor.android.ui.features.login.LoginScreen
import com.spyrdonapps.weightreductor.android.ui.features.onboarding.OnboardingScreen
import com.spyrdonapps.weightreductor.android.ui.features.register.RegisterScreen

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
    val actions = remember(navController) { AppActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppDestinations.ONBOARDING_ROUTE) {
            OnboardingScreen(goToLoginScreen = actions.goToLoginScreen)
        }
        composable(AppDestinations.LOGIN_ROUTE) {
            LoginScreen(goToRegisterScreen = actions.goToRegisterScreen, goToHomeScreen = actions.goToHomeScreen)
        }
        composable(AppDestinations.REGISTER_ROUTE) {
            RegisterScreen(goToLoginScreen = actions.goToLoginScreen, goToHomeScreen = actions.goToHomeScreen)
        }
        composable(AppDestinations.HOME_ROUTE) {
            HomeScreen(goToSettings = {  })
        }
    }
}
