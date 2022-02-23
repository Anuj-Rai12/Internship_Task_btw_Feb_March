package com.example.relevel.model.users


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("appId") val appId: String,
    @SerializedName("blocks") val blocks: List<Any>,
    @SerializedName("host") val host: Host,
    @SerializedName("_id") val id: String,
    @SerializedName("members") val members: List<Member>,
    @SerializedName("modHistory") val modHistory: List<String>,
    @SerializedName("moderators") val moderators: List<Moderator>,
    @SerializedName("raiseAllow") val raiseAllow: Boolean,
    @SerializedName("requests") val requests: List<Any>,
    @SerializedName("roomid") val roomid: String,
    @SerializedName("speakers") val speakers: List<Speaker>,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("token") val token: String,
    @SerializedName("topic") val topic: String,
    @SerializedName("total_members") val totalMembers: Int,
    @SerializedName("type") val type: Int,
    @SerializedName("__v") val v: Int,
    @SerializedName("version") val version: Int,
    @SerializedName("volume") val volume: Int
)