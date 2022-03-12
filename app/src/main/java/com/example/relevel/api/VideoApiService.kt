package com.example.relevel.api

import com.example.relevel.model.MainVideoDataCls
import com.example.relevel.utils.UtilsFiles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApiService {
    @GET(UtilsFiles.ApiService.END_POINT)
    suspend fun getVideoToStream(@Query("p") query: String): Response<MainVideoDataCls>
}