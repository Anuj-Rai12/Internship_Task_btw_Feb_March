package com.example.relevel.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.relevel.R
import com.example.relevel.adaptor.viewHolder.ItemMemberClicked
import com.example.relevel.adaptor.viewHolder.MainViewHolder
import com.example.relevel.databinding.AudienceItemLayoutBinding
import com.example.relevel.databinding.MainMemberLitemLayoutBinding
import com.example.relevel.databinding.OtherUserTitleLayoutBinding
import com.example.relevel.model.sealed.SealedUser
import java.lang.IllegalArgumentException

class MainAdaptor(private val itemMemberClicked: ItemMemberClicked) :
    ListAdapter<SealedUser, MainViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<SealedUser>() {
            override fun areItemsTheSame(oldItem: SealedUser, newItem: SealedUser): Boolean {
                return getData(oldItem) == getData(newItem)
            }

            override fun areContentsTheSame(oldItem: SealedUser, newItem: SealedUser): Boolean {
                return oldItem == newItem
            }
        }

        private fun getData(item: SealedUser): String {
            return when (item) {
                is SealedUser.Member -> item.id
                is SealedUser.Title -> item.title
                is SealedUser.Users -> item.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return when (viewType) {
            R.layout.audience_item_layout -> {
                 MainViewHolder.OtherMember(
                    binding = AudienceItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.layout.main_member_litem_layout -> {
                 MainViewHolder.AllAllowedMember(
                    binding = MainMemberLitemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.layout.other_user_title_layout -> {
                 MainViewHolder.OtherTitleLayout(
                    binding = OtherUserTitleLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw  IllegalArgumentException("Cannot set Item at Recycle")
            }
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val curr = getItem(position)
        curr?.let {
            when (holder) {
                is MainViewHolder.AllAllowedMember -> holder.setData(it as SealedUser.Users,itemMemberClicked)
                is MainViewHolder.OtherMember -> holder.setData(it as SealedUser.Member,itemMemberClicked)
                is MainViewHolder.OtherTitleLayout -> holder.setData(it as SealedUser.Title)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SealedUser.Member -> R.layout.audience_item_layout
            is SealedUser.Title -> R.layout.other_user_title_layout
            is SealedUser.Users -> R.layout.main_member_litem_layout
        }
    }
}