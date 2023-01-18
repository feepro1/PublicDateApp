package com.main.chats.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.main.chats.R
import com.main.chats.data.entities.LikeFromUser
import com.main.chats.databinding.ItemLikeBinding
import com.main.core.UsernameUi

class LikesAdapter : RecyclerView.Adapter<LikesAdapter.LikesViewHolder>(), UsernameUi {
    private val likes = mutableListOf<LikeFromUser>()

    class LikesViewHolder(item: View): ViewHolder(item) {
        private val binding by lazy { ItemLikeBinding.bind(item) }
        fun bind(likeFromUser: LikeFromUser, usernameUi: UsernameUi) {
            binding.tvUsername.text = usernameUi.mapUsername(likeFromUser.firstName, likeFromUser.lastName)
            Glide.with(itemView).load(likeFromUser.avatarUrl).into(binding.ivUserIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_like, parent, false)
        return LikesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikesViewHolder, position: Int) {
        holder.bind(likes[position], this)
    }

    override fun getItemCount() = likes.size
    override fun mapUsername(firstName: String, lastName: String): String {
        return "$firstName $lastName"
    }
}