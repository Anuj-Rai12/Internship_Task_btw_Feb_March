package com.example.relevel.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.relevel.repo.VideoRepositoryImpl
import com.example.relevel.utils.ApiResponse
import com.example.relevel.utils.Event
import com.example.relevel.utils.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModels @Inject constructor(
    private val repositoryImpl: VideoRepositoryImpl,
    application: Application
) : ViewModel() {


    private val _events = MutableLiveData<Event<String>>()
    val events: LiveData<Event<String>>
        get() = _events


    private val _data = MutableLiveData<ApiResponse<out Any?>>()
    val data: LiveData<ApiResponse<out Any?>>
        get() = _data

    init {
        if (application.isNetworkAvailable()) {
            viewModelScope.launch {
                repositoryImpl.getVideoResponse().collectLatest {
                    _data.postValue(it)
                }
            }
        } else {
            _events.postValue(Event("No Internet Connection"))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}