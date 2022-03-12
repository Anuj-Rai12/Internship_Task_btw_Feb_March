package com.example.relevel.repo

import android.util.Log
import com.example.relevel.api.VideoApiService
import com.example.relevel.utils.ApiResponse
import com.example.relevel.utils.TAG
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val api: VideoApiService) : VideoRepository {
    override fun getVideoResponse(query: String) = flow {
        emit(ApiResponse.Loading("Please Wait"))
        val data = try {
            val info = api.getVideoToStream(query)
            val res = if (info.isSuccessful) {
                info.body()
            } else {
                Log.i(TAG, "getVideoResponse: ${info.message()}")
                null
            }
            ApiResponse.Success(res)
        } catch (e: Exception) {
            ApiResponse.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)

}