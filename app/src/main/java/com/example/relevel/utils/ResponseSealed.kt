package com.example.relevel.utils

sealed class ResponseSealed<T>(
    val data: T? = null,
    val exception: Exception? = null
) {
    class Loading<T>(data: T?) : ResponseSealed<T>(data)
    class Success<T>(data: T) : ResponseSealed<T>(data)
    class Error<T>(data: T? = null, exception: Exception?) : ResponseSealed<T>(data, exception)
}