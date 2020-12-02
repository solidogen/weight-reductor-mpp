package com.spyrdonapps.weightreductor.android.ui.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import com.spyrdonapps.common.model.ShoppingListItem
import com.spyrdonapps.common.repository.SampleClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity() {

    // todo viewmodel
    private val textStateFlow: MutableStateFlow<List<ShoppingListItem>> =
        MutableStateFlow(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout(textStateFlow)
        }
        getHome()
    }

    private fun getHome() {
        textStateFlow.value = listOf(ShoppingListItem(0, "LOADING", 0))
        lifecycleScope.launchWhenCreated {
            val repo = getKoin().get<SampleClientRepository>()
            try {
                repo.fetchHome().let { list ->
                    textStateFlow.value = list
                }
            } catch (e: Throwable) {
                textStateFlow.value = listOf(ShoppingListItem(0, "ERROR: ${e.message}", 0))
            }
        }
    }
}

@Composable
fun MainLayout(textStateFlow: MutableStateFlow<List<ShoppingListItem>>) {
    val textState = textStateFlow.collectAsState()
    LazyColumnFor(items = textState.value, itemContent = { item ->
        Text(text = "${item.name} ${item.id} ${item.priority}")
    })
}