package com.example.relevel.adaptor.viewHolder

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.relevel.databinding.AudienceItemLayoutBinding
import com.example.relevel.databinding.MainMemberLitemLayoutBinding
import com.example.relevel.databinding.OtherUserTitleLayoutBinding
import com.example.relevel.model.sealed.SealedUser
import com.example.relevel.utils.TAG

sealed class MainViewHolder(viewBinding: ViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    class AllAllowedMember(
        private val binding: MainMemberLitemLayoutBinding
    ) : MainViewHolder(binding) {
        private lateinit var innerRecyclerView: InnerRecyclerView

        @SuppressLint("NotifyDataSetChanged")
        fun setData(users: SealedUser.Users, itemClicked: ItemMemberClicked) {
            binding.mainMemberRecycle.apply {
                innerRecyclerView = InnerRecyclerView {
                    itemClicked(it)
                }
                layoutManager = GridLayoutManager(users.context, 3)
                adapter = innerRecyclerView
            }
            innerRecyclerView.notifyDataSetChanged()
            innerRecyclerView.submitList(users.list)
        }
    }

    class OtherTitleLayout(
        private val binding: OtherUserTitleLayoutBinding
    ) : MainViewHolder(binding) {

        fun setData(tile: SealedUser.Title) {
            binding.otherDecTxt.text = tile.title
        }
    }


    class OtherMember(
        private val binding: AudienceItemLayoutBinding
    ) : MainViewHolder(binding) {
        private lateinit var innerRecyclerView: InnerRecyclerView

        @SuppressLint("NotifyDataSetChanged")
        fun setData(member: SealedUser.Member, itemClicked: ItemMemberClicked) {
            binding.test.apply {
                innerRecyclerView = InnerRecyclerView {
                    itemClicked(it)
                }
                layoutManager = GridLayoutManager(member.context, 3)
                adapter = innerRecyclerView
            }
            innerRecyclerView.notifyDataSetChanged()
            innerRecyclerView.submitList(member.list)
        }
    }
}