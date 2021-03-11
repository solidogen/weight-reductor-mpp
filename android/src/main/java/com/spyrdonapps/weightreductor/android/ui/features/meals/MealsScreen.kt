package com.spyrdonapps.weightreductor.android.ui.features.meals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spyrdonapps.weightreductor.android.ui.features.main.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MealsScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = getViewModel()
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.Blue)) {

    }
}