package com.example.relevel.model.users


import com.google.gson.annotations.SerializedName

data class CilentDataResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val `data`: Data,
    @SerializedName("msg") val msg: Any?
)