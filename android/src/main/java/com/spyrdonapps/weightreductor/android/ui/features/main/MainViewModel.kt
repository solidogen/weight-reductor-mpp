package com.spyrdonapps.weightreductor.android.ui.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.repository.WeighingRepository
import com.spyrdonapps.weightreductor.android.util.Event
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class MainViewModel(
    private val sampleClientRepository: WeighingRepository,
    private val logger: Kermit
) : ViewModel() {

    private val _weighingsLiveData: MutableLiveData<List<Weighing>> = MutableLiveData()
    private val _errorLiveData: MutableLiveData<Event<String>> = MutableLiveData()

    val weighingsLiveData: LiveData<List<Weighing>> get() = _weighingsLiveData
    val errorLiveData: LiveData<Event<String>> get() = _errorLiveData

    fun postWeighing() {
        viewModelScope.launch {
            try {
                sampleClientRepository.postWeighing(
                    Weighing(
                        weight = 13f,
                        date = Clock.System.now()
                    )
                )
            } catch (e: Throwable) {
                logger.e(e) { "Error posting a weighing" }
                _errorLiveData.value = Event(e.message.orEmpty())
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
                    _weighingsLiveData.value = list
                }
            } catch (e: Throwable) {
                logger.e(e) { "Error getting weighings" }
                _errorLiveData.value = Event(e.message.orEmpty())
            }
        }
    }
}