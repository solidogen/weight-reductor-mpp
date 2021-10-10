package com.spyrdonapps.weightreductor.android.ui.features.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spyrdonapps.common.util.utils.Action
import com.spyrdonapps.weightreductor.android.ui.features.main.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = getViewModel(),
    goToSettings: Action
) {
    Box(modifier = modifier) {
        Button(onClick = goToSettings::invoke) {
            Text(text = "Go to Settings")
        }
    }
}