package com.spyrdonapps.weightreductor.android.ui.features.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegisterScreen(
    goToLoginScreen: () -> Unit,
    goToHomeScreen: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Register:")
        Button(onClick = goToHomeScreen) {
            Text(text = "Register")
        }
        Button(onClick = goToLoginScreen) {
            Text(text = "Already have the account?")
        }
    }
}