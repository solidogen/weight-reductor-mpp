package com.spyrdonapps.weightreductor.android.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout(viewModel)
        }
    }
}

@Composable
fun MainLayout(viewModel: MainViewModel) {
    val textState = viewModel.textStateFlow.collectAsState()
    LazyColumnFor(items = textState.value, itemContent = { item ->
        Text(text = "${item.name} ${item.id} ${item.priority}")
    })
}