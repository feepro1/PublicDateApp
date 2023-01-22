package com.main.chat.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.main.chat.R
import com.main.chat.data.MessageType
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.databinding.ItemReceivedMessageBinding
import com.main.chat.databinding.ItemSentMessageBinding

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {
    private val messages = mutableListOf<MessageCacheModel>()

    class MessagesViewHolder(item: View): ViewHolder(item) {
        private val receivedMessageBinding by lazy { ItemReceivedMessageBinding.bind(item) }
        private val sentMessageBinding by lazy { ItemSentMessageBinding.bind(item) }

        fun bind(messageCacheModel: MessageCacheModel) {
            val myUid = Firebase.auth.currentUser?.uid.toString()
            if (messageCacheModel.senderUid == myUid) {
                sentMessageBinding.tvMessage.text = messageCacheModel.message
            }
            if (messageCacheModel.receiverUid == myUid) {
                receivedMessageBinding.tvMessage.text = messageCacheModel.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        return when(viewType) {
            MessageType.SentMessage.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sent_message, parent, false)
                MessagesViewHolder(view)
            }
            MessageType.ReceivedMessage.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_received_message, parent, false)
                MessagesViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sent_message, parent, false)
                MessagesViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount() = messages.size
}