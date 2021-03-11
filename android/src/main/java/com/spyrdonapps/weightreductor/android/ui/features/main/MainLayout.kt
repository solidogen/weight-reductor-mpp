package com.spyrdonapps.weightreductor.android.ui.features.main

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.spyrdonapps.weightreductor.android.ui.custom.AppNavGraph
import com.spyrdonapps.weightreductor.android.util.localBackDispatcher

@Composable
fun MainLayout(backDispatcher: OnBackPressedDispatcher) {
    CompositionLocalProvider(localBackDispatcher provides backDispatcher) {
        AppNavGraph()
    }
}