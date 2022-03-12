package com.example.relevel.repo

import com.example.relevel.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun getVideoResponse(query: String = "showAllVideos"): Flow<ApiResponse<out Any?>>
}