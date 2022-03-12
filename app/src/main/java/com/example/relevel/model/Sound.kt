package com.example.relevel.model


import com.google.gson.annotations.SerializedName

data class Sound(
    @SerializedName("audio_path") val audioPath: AudioPath,
    @SerializedName("created") val created: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id1: Int,
    @SerializedName("_id") val id: String,
    @SerializedName("section") val section: String,
    @SerializedName("sound_name") val soundName: String,
    @SerializedName("thum") val thum: String
)