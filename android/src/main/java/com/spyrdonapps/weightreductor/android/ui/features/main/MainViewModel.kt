package com.spyrdonapps.weightreductor.android.ui.features.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.TokenData
import com.spyrdonapps.common.model.UserCredentials
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.repository.ClientRepository
import com.spyrdonapps.weightreductor.android.util.Event
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class MainViewModel(
    private val repository: ClientRepository,
    private val logger: Kermit
) : ViewModel() {

    private val _weighingsLiveData: MutableLiveData<List<Weighing>> = MutableLiveData()
    private val _errorLiveData: MutableLiveData<Event<String>> = MutableLiveData()
    private val _tokenData: MutableLiveData<TokenData?> = MutableLiveData(null)

    val weighingsLiveData: LiveData<List<Weighing>> get() = _weighingsLiveData
    val errorLiveData: LiveData<Event<String>> get() = _errorLiveData
    val tokenDataLiveData: LiveData<TokenData?> get() = _tokenData

    fun loginRequested(userCredentials: UserCredentials) {
        viewModelScope.launch {
            try {
                val tokenData = repository.login(userCredentials)
                Log.d("MVM", "TokenData: $tokenData")
                _tokenData.postValue(tokenData)
            } catch (e: Exception) {
                logger.e(e) { "Error trying to login" }
                _errorLiveData.value = Event(e.message.orEmpty())
            }
        }
    }

    fun registerRequested(userCredentials: UserCredentials) {
        viewModelScope.launch {
            try {
                val tokenData = repository.register(userCredentials)
                Log.d("MVM", "TokenData: $tokenData")
                _tokenData.postValue(tokenData)
            } catch (e: Exception) {
                logger.e(e) { "Error trying to register" }
                _errorLiveData.value = Event(e.message.orEmpty())
            }
        }
    }

    fun postWeighing() {
        viewModelScope.launch {
            try {
                repository.postWeighing(
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
                repository.getAllWeighings().let { list ->
                    _weighingsLiveData.value = list
                }
            } catch (e: Throwable) {
                logger.e(e) { "Error getting weighings" }
                _errorLiveData.value = Event(e.message.orEmpty())
            }
        }
    }
}