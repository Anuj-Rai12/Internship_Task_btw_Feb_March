package com.example.relevel.usecase

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.relevel.R
import com.example.relevel.model.sealed.MemberWhile
import com.example.relevel.model.sealed.SealedUser
import com.example.relevel.model.users.CilentDataResponse
import com.example.relevel.utils.TAG
import javax.inject.Inject

class MainUserCase @Inject constructor(private val application: Application) {

    fun getApiData(
        data: CilentDataResponse,
        context: Context,
        title: String
    ): MutableList<SealedUser> {
        val arr = mutableListOf<SealedUser>()
        val member = mutableListOf<MemberWhile>()
        val other = mutableListOf<MemberWhile>()
        val host = data.data.host
        member.add(
            MemberWhile(
                host.au, host.id, host.mic, host.mod, host.n, host.socketid, host.u, host.visible,
                application.getString(R.string.host_txt)
            )
        )
        data.data.speakers.forEach {
            member.add(
                MemberWhile(
                    it.au,
                    it.id,
                    it.mic,
                    it.mod,
                    it.n,
                    it.socketid,
                    it.u,
                    it.visible,
                    application.getString(R.string.speaker_txt)
                )
            )
        }

        data.data.moderators.forEach {
            member.add(
                MemberWhile(
                    it.au,
                    it.id,
                    it.mic,
                    it.mod,
                    it.n,
                    it.socketid,
                    it.u,
                    it.visible,
                    application.getString(R.string.mod_txt)
                )
            )
        }
        arr.add(SealedUser.Users(id = "1", member, context))
        arr.add(SealedUser.Title(title))
        data.data.members.forEach {
            other.add(
                MemberWhile(
                    it.au,
                    it.id,
                    it.mic,
                    it.mod,
                    it.n,
                    it.socketid,
                    it.u,
                    it.visible
                )
            )
        }
        arr.add(SealedUser.Member("2", other, context))
        Log.i(TAG, "getApiData: $arr")
        return arr
    }
}