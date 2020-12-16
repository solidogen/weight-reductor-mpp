package com.spyrdonapps.weightreductor.android.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.ShoppingListItem
import com.spyrdonapps.common.repository.SampleClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val sampleClientRepository: SampleClientRepository,
    private val logger: Kermit
) : ViewModel() {

    val textStateFlow: MutableStateFlow<List<ShoppingListItem>> =
        MutableStateFlow(emptyList())

    init {
        getHome()
    }

    private fun getHome() {
        textStateFlow.value = listOf(ShoppingListItem(0, "LOADING", 0))
        viewModelScope.launch {
            try {
                sampleClientRepository.fetchHome().let { list ->
                    textStateFlow.value = list
                }
            } catch (e: Throwable) {
                textStateFlow.value = listOf(ShoppingListItem(0, "ERROR: ${e.message}", 0))
            }
        }
    }
}