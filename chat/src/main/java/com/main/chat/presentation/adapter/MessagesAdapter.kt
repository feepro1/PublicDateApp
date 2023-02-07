package com.main.chat.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.main.chat.R
import com.main.chat.data.MessageType
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.databinding.ItemReceivedMessageBinding
import com.main.chat.databinding.ItemSentMessageBinding
import com.main.chat.domain.ManageChatAdapterData

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>(), ManageChatAdapterData {
    private val messages = mutableListOf<MessageCacheModel>()

    class MessagesViewHolder(item: View): ViewHolder(item) {
        private val receivedMessageBinding by lazy { ItemReceivedMessageBinding.bind(item) }
        private val sentMessageBinding by lazy { ItemSentMessageBinding.bind(item) }

        fun bind(messageCacheModel: MessageCacheModel) {
            val myUid = Firebase.auth.currentUser?.uid.toString()
            if (messageCacheModel.senderUid == myUid) {
                sentMessageBinding.tvMessage.text = messageCacheModel.message
                sentMessageBinding.tvMessageTime.text = messageCacheModel.dateTimeMillis.toString()
            }
            if (messageCacheModel.receiverUid == myUid) {
                receivedMessageBinding.tvMessage.text = messageCacheModel.message
                receivedMessageBinding.tvMessageTime.text = messageCacheModel.dateTimeMillis.toString()
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
        return if (messages[position].receiverUid == Firebase.auth.currentUser?.uid.toString()) {
            MessageType.ReceivedMessage.ordinal
        } else if (messages[position].senderUid == Firebase.auth.currentUser?.uid.toString()) {
            MessageType.SentMessage.ordinal
        } else {
            MessageType.Unconfirmed.ordinal
        }
    }

    override fun mapAll(messages: List<MessageCacheModel>) {
        val diff = MessageDiffUtilCallback(this.messages, messages)
        val result = DiffUtil.calculateDiff(diff)
        this.messages.clear()
        this.messages.addAll(messages)
        this.messages.sortBy { messageCacheModel -> messageCacheModel.dateTimeMillis }
        result.dispatchUpdatesTo(this)
    }

    override fun map(message: MessageCacheModel) {
        val newMessages = messages + message
        val diff = MessageDiffUtilCallback(this.messages, newMessages)
        val result = DiffUtil.calculateDiff(diff)
        this.messages.add(message)
        this.messages.sortBy { messageCacheModel -> messageCacheModel.dateTimeMillis }
        result.dispatchUpdatesTo(this)
    }

    override fun mapAllWithoutClear(messages: List<MessageCacheModel>) {
        val diff = MessageDiffUtilCallback(this.messages, messages)
        val result = DiffUtil.calculateDiff(diff)
        this.messages.addAll(messages)
        this.messages.sortBy { messageCacheModel -> messageCacheModel.dateTimeMillis }
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = messages.size
}

class MessageDiffUtilCallback(
    private val oldList: List<MessageCacheModel>,
    private val newList: List<MessageCacheModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].dateTimeMillis == newList[newItemPosition].dateTimeMillis &&
        oldList[oldItemPosition].message == newList[newItemPosition].message &&
        oldList[oldItemPosition].receiverUid == newList[newItemPosition].receiverUid &&
        oldList[oldItemPosition].senderUid == newList[newItemPosition].senderUid

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}