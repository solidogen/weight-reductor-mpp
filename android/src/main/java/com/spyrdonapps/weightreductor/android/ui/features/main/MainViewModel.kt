package com.spyrdonapps.weightreductor.android.ui.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.repository.WeighingRepository
import com.spyrdonapps.weightreductor.android.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant

class MainViewModel(
    private val sampleClientRepository: WeighingRepository,
    private val logger: Kermit
) : ViewModel() {

    private val _textStateFlow: MutableStateFlow<List<Weighing>> = MutableStateFlow(emptyList())
    private val _errorFlow: MutableLiveData<Event<String>> = MutableLiveData()

    val textStateFlow: StateFlow<List<Weighing>> get() = _textStateFlow
    val errorFlow: LiveData<Event<String>> get() = _errorFlow

    init {
        getHome()
    }

    fun postWeighing() {
        viewModelScope.launch {
            try {
                sampleClientRepository.postWeighing(
                    Weighing(
                        weight = 13f,
                        date = Instant.fromEpochMilliseconds(System.currentTimeMillis())
                    )
                )
            } catch (e: Throwable) {
                logger.e(e) { "Error posting a weighing" }
                _errorFlow.value = Event(e.message.orEmpty())
            }
        }
    }

    fun reloadWeighings() {
        getHome()
    }

    private fun getHome() {
        viewModelScope.launch {
            try {
                sampleClientRepository.getAllWeighings().let { list ->
                    _textStateFlow.value = list
                }
            } catch (e: Throwable) {
                logger.e(e) { "Error getting weighings" }
                _errorFlow.value = Event(e.message.orEmpty())
            }
        }
    }
}