package com.example.relevel.model


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("fb_id") val fbId: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("_id") val id: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("profile_pic") val profilePic: String,
    @SerializedName("username") val username: String,
    @SerializedName("verified") val verified: String
)