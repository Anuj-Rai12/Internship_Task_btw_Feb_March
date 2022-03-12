package com.example.relevel.model


import com.google.gson.annotations.SerializedName

data class MainVideoDataCls(
    @SerializedName("code") val code: String,
    @SerializedName("msg") val msg: List<Msg>,
    @SerializedName("s") val s: String
)