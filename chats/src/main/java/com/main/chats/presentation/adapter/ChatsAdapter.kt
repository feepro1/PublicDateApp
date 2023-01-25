package com.main.chats.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.main.chats.R
import com.main.chats.data.entities.Chat
import com.main.chats.databinding.ItemChatBinding
import com.main.chats.domain.ManageChatsAdapterData
import com.main.core.UsernameUi

class ChatsAdapter(
    private val clickListenerChat: ChatCLickListener
) : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>(), UsernameUi, ManageChatsAdapterData {
    private val chats = mutableListOf<Chat>(
        Chat("Vadym", "Hrynyk", uid = "o3ll7C8ntpf4IyFiZ4mbIzOhdZA2")
    )

    class ChatsViewHolder(item: View): ViewHolder(item) {
        private val binding by lazy { ItemChatBinding.bind(item) }
        fun bind(chat: Chat, usernameUi: UsernameUi, clickListenerChat: ChatCLickListener) {
            binding.tvLastMessage.text = chat.lastMessage
            binding.tvUsername.text = usernameUi.mapUsername(chat.firstName, chat.lastName)
            Glide.with(itemView).load(chat.avatarUrl).into(binding.ivUserIcon)
            binding.ivUserIcon.setOnClickListener { clickListenerChat.iconClick(chat) }
            binding.itemChat.setOnClickListener { clickListenerChat.itemClick(chat) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind(chats[position], this, clickListenerChat)
    }

    override fun getItemCount() = chats.size

    override fun mapUsername(firstName: String, lastName: String): String {
        return "$firstName $lastName"
    }

    override fun mapAll(newChats: List<Chat>) {
        val diff = ChatDiffUtilCallback(chats, newChats)
        val result = DiffUtil.calculateDiff(diff)
        chats.clear()
        chats.addAll(newChats)
        result.dispatchUpdatesTo(this)
    }
}

interface ChatCLickListener {

    fun iconClick(chat: Chat)

    fun itemClick(chat: Chat)
}

class ChatDiffUtilCallback(
    private val oldList: List<Chat>,
    private val newList: List<Chat>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].uid == (newList[newItemPosition]).uid

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}