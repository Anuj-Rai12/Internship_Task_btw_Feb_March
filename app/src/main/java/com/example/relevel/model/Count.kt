package com.example.relevel.model


import com.google.gson.annotations.SerializedName

data class Count(
    @SerializedName("_id") val id: String,
    @SerializedName("like_count") val likeCount: Int,
    @SerializedName("video_comment_count") val videoCommentCount: Int,
    @SerializedName("view") val view: Int
)