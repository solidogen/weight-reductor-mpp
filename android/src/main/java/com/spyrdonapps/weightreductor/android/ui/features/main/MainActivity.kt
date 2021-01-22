package com.spyrdonapps.weightreductor.android.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    // todo koin-compose when 3.0.0 stable is available
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout(
                onBackPressedDispatcher = onBackPressedDispatcher,
                viewModel = viewModel
            )
        }
    }
}