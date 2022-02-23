package com.example.relevel.repo

import com.example.relevel.api.ApiService
import com.example.relevel.utils.ResponseSealed
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ApiService) {

    fun getAllData() = flow {
        emit(ResponseSealed.Loading("Please Wait..."))
        kotlinx.coroutines.delay(2000)
        val data = try {
            val info = api.getAllData()
            val send = if (info.isSuccessful) {
                info.body()
            } else
                null
            ResponseSealed.Success(send)
        } catch (e: Exception) {
            ResponseSealed.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)
}