package com.spyrdonapps.weightreductor.android.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.repository.WeighingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val sampleClientRepository: WeighingRepository,
    private val logger: Kermit
) : ViewModel() {

    val textStateFlow: MutableStateFlow<List<Weighing>> = MutableStateFlow(emptyList())

    init {
        getHome()
    }

    private fun getHome() {
        viewModelScope.launch {
            try {
                sampleClientRepository.getAllWeighings().let { list ->
                    textStateFlow.value = list
                }
            } catch (e: Throwable) {
                // todo errorManager? no idea how to do this in compose
            }
        }
    }
}