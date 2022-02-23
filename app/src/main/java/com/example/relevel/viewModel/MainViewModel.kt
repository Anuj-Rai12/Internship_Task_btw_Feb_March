package com.example.relevel.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.relevel.model.users.CilentDataResponse
import com.example.relevel.repo.MainRepository
import com.example.relevel.usecase.MainUserCase
import com.example.relevel.utils.Event
import com.example.relevel.utils.ResponseSealed
import com.example.relevel.utils.TAG
import com.example.relevel.utils.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val userCase: MainUserCase
) : ViewModel() {

    private val _getApiData = MutableLiveData<ResponseSealed<out Any?>>()
    val getAllData: LiveData<ResponseSealed<out Any?>>
        get() = _getApiData


    private val _eventHandle = MutableLiveData<Event<String>>()
    val eventHandle: LiveData<Event<String>>
        get() = _eventHandle

    init {
        Log.i(TAG, "NetWork: ${application.isNetworkAvailable()}")
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

    fun getSealedClass(data: CilentDataResponse, context: Context, title: String)=
        userCase.getApiData(data, context, title)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}