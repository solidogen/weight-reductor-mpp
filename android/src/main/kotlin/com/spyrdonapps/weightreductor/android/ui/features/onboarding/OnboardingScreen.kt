package com.spyrdonapps.weightreductor.android.ui.features.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OnboardingScreen(
    goToLoginScreen: () -> Unit
) {
    Scaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "App is this and that")
            Button(onClick = goToLoginScreen) {
                Text(text = "Go To Login")
            }
        }
    }
}