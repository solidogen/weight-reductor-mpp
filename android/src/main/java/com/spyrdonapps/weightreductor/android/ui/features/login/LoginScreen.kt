package com.spyrdonapps.weightreductor.android.ui.features.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    goToRegisterScreen: () -> Unit,
    goToHomeScreen: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Login:")
        Button(onClick = goToHomeScreen) {
            Text(text = "Login")
        }
        Button(onClick = goToRegisterScreen) {
            Text(text = "Don't have the account?")
        }
    }
}