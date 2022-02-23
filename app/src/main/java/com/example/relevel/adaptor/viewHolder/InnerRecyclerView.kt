package com.example.relevel.adaptor.viewHolder

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.relevel.R
import com.example.relevel.databinding.UserProfileItemLayoutBinding
import com.example.relevel.model.sealed.MemberWhile
import com.example.relevel.utils.AllConstString
import com.example.relevel.utils.hide
import com.example.relevel.utils.loadImage


typealias ItemMemberClicked = (data: MemberWhile) -> Unit

class InnerRecyclerView(private val itemClicked: ItemMemberClicked) :
    ListAdapter<MemberWhile, InnerRecyclerView.UserProfileViewHolder>(diffUtil) {
    inner class UserProfileViewHolder(private val binding: UserProfileItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.M)
        fun makeData(data: MemberWhile, itemClicked: ItemMemberClicked) {
            binding.apply {
                if (data.type == null)
                    profilePic.loadImage(AllConstString.getImageProfileUrl(data.u),20f)
                else
                    profilePic.loadImage(AllConstString.getImageProfileUrl(data.u))
                userNameTxt.text = data.n
                userMicBtn.isVisible = data.mic
                data.type?.let { userDescTxt.text = it } ?: userDescTxt.hide()
                root.apply {
                    if (data.type == null) {
                        setBackgroundColor(context.resources.getColor(R.color.white, null))
                    }
                    setOnClickListener {
                        itemClicked(data)
                    }
                }
                if (data.type != null &&
                    data.type == userNameTxt.context.getString(R.string.host_txt)
                    || data.mod
                ) {
                    val leftDrawable = AppCompatResources
                        .getDrawable(userNameTxt.context, R.drawable.ic_host_badge)
                    userNameTxt.setCompoundDrawablesWithIntrinsicBounds(
                        leftDrawable,
                        null,
                        null,
                        null
                    )
                }

            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MemberWhile>() {
            override fun areItemsTheSame(
                oldItem: MemberWhile,
                newItem: MemberWhile
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MemberWhile,
                newItem: MemberWhile
            ) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        val binding =
            UserProfileItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserProfileViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        val currItem = getItem(position)
        currItem?.let {
            holder.makeData(it, itemClicked)
        }
    }

}
