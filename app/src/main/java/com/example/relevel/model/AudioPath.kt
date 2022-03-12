package com.example.relevel.model


import com.google.gson.annotations.SerializedName

data class AudioPath(
    @SerializedName("acc") val acc: String,
    @SerializedName("mp3") val mp3: String
)