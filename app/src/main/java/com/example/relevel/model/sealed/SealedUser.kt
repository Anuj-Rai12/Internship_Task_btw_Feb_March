package com.example.relevel.model.sealed

import android.content.Context

sealed class SealedUser {
    data class Users(
        val id: String,
        val list: List<MemberWhile>,
        val context: Context
    ) : SealedUser()

    data class Title(
        val title: String
    ) : SealedUser()

    data class Member(
        val id: String,
        val list: List<MemberWhile>,
        val context: Context
    ) : SealedUser()
}


data class MemberWhile(
    val au: Int,
    val id: String,
    val mic: Boolean,
    val mod: Boolean,
    val n: String,
    val socketid: String,
    val u: String,
    val visible: Boolean,
    val type: String? = null
)