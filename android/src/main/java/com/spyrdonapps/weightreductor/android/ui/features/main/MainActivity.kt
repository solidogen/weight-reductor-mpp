package com.spyrdonapps.weightreductor.android.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.snackbar.Snackbar
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.weightreductor.R
import com.spyrdonapps.weightreductor.android.util.observeEvents
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout(viewModel)
        }
        // todo learn how to integrate this inside compose
        viewModel.errorFlow.observeEvents(this) {
            Snackbar.make(window.decorView.findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT).apply {
                setBackgroundTint(context.getColor(R.color.teal_700))
            }.show()
        }
    }
}

@Composable
fun MainLayout(viewModel: MainViewModel) {
    val textState = viewModel.textStateFlow.collectAsState()
    Column {
        Button(onClick = { viewModel.postWeighing() }) {
            Text(text = "Click to post weighing")
        }
        Button(onClick = { viewModel.reloadWeighings() }) {
            Text(text = "Reload weighings")
        }
        LazyColumnFor(items = textState.value, itemContent = { item ->
            Text(text = "${item.weight} ${item.date}")
        })
    }
}

// fixme - it works but I guess this can be much better
@Preview
@Composable
fun MainLayoutPreview() {
    initKoin {  }
    val koin = object : KoinComponent {}.getKoin()
    MainLayout(viewModel = MainViewModel(koin.get(), koin.get()))
}