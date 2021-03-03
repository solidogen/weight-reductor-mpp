package com.spyrdonapps.weightreductor.android.ui.features.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spyrdonapps.weightreductor.android.ui.features.main.MainViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    goToSettings: () -> Unit
) {
    Box(modifier = modifier) {
        Button(onClick = goToSettings) {
            Text(text = "Go to Settings")
        }
    }
}