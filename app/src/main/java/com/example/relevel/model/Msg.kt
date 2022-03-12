package com.example.relevel.model


import com.google.gson.annotations.SerializedName

data class Msg(
    @SerializedName("city") val city: String,
    @SerializedName("count") val count: Count,
    @SerializedName("country") val country: String,
    @SerializedName("created") val created: String,
    @SerializedName("description") val description: String,
    @SerializedName("fb_id") val fbId: String,
    @SerializedName("gif") val gif: String,
    @SerializedName("_id") val id1: String,
    @SerializedName("id") val id: Int,
    @SerializedName("is_block") val isBlock: Int,
    @SerializedName("liked") val liked: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("sound") val sound: Sound,
    @SerializedName("status") val status: String,
    @SerializedName("thum") val thum: String,
    @SerializedName("tp") val tp: Int,
    @SerializedName("uid") val uid: String,
    @SerializedName("user_info") val userInfo: UserInfo,
    @SerializedName("__v") val v: Int,
    @SerializedName("video") val video: String
)