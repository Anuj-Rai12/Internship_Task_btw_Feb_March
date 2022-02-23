package com.example.relevel.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.relevel.repo.MainRepository
import com.example.relevel.utils.Event
import com.example.relevel.utils.ResponseSealed
import com.example.relevel.utils.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application
) : ViewModel() {

    private val _getApiData = MutableLiveData<ResponseSealed<out Any?>>()
    val getAllData: LiveData<ResponseSealed<out Any?>>
        get() = _getApiData


    private val _eventHandle = MutableLiveData<Event<String>>()
    val eventHandle: LiveData<Event<String>>
        get() = _eventHandle

    init {
        if (application.isNetworkAvailable()) {
            viewModelScope.launch {
                mainRepository.getAllData().collectLatest {
                    _getApiData.postValue(it)
                }
            }
        } else {
            _eventHandle.postValue(Event("No Internet Connection"))
        }

    }



    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}