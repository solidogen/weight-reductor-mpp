package com.spyrdonapps.weightreductor.android.ui.features.main

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import com.spyrdonapps.weightreductor.android.ui.features.home.HomeScreen
import com.spyrdonapps.weightreductor.android.ui.features.settings.SettingsScreen
import com.spyrdonapps.weightreductor.android.util.Actions
import com.spyrdonapps.weightreductor.android.util.Destination
import com.spyrdonapps.weightreductor.android.util.Navigator
import com.spyrdonapps.common.util.utils.exhaustive

@Composable
fun MainLayout(onBackPressedDispatcher: OnBackPressedDispatcher, viewModel: MainViewModel) {
    val navigator: Navigator<Destination> = rememberSavedInstanceState(
        saver = Navigator.saver<Destination>(onBackPressedDispatcher)
    ) {
        Navigator(Destination.Home, onBackPressedDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }
    // todo app theme
    Crossfade(current = navigator.current) { destination ->
        when (destination) {
            Destination.Home -> HomeScreen(viewModel = viewModel, goToSettings = actions.goToSettings)
            Destination.Settings -> SettingsScreen(viewModel = viewModel, upPress = actions.upPress)
        }.exhaustive
    }
}