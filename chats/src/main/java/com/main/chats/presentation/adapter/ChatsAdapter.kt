package com.main.chats.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.main.chats.R
import com.main.chats.data.entities.Chat
import com.main.chats.databinding.ItemChatBinding
import com.main.core.UsernameUi

class ChatsAdapter : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>(), UsernameUi {
    private val chats = mutableListOf<Chat>()

    class ChatsViewHolder(item: View): ViewHolder(item) {
        private val binding by lazy { ItemChatBinding.bind(item) }
        fun bind(chat: Chat, usernameUi: UsernameUi) {
            binding.tvLastMessage.text = chat.lastMessage
            binding.tvUsername.text = usernameUi.mapUsername(chat.firstName, chat.lastName)
            Glide.with(itemView).load(chat.avatarUrl).into(binding.ivUserIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind(chats[position], this)
    }

    override fun getItemCount() = chats.size

    override fun mapUsername(firstName: String, lastName: String): String {
        return "$firstName $lastName"
    }
}