package com.main.chats.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.main.chats.data.entities.LikeFromUser

class LikesAdapter : RecyclerView.Adapter<LikesAdapter.LikesViewHolder>() {
    private val likes = mutableListOf<LikeFromUser>()

    class LikesViewHolder(item: View): ViewHolder(item) {
        fun bind(likeFromUser: LikeFromUser) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikesViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: LikesViewHolder, position: Int) {
        holder.bind(likes[position])
    }

    override fun getItemCount() = likes.size
}