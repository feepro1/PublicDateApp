package com.main.chats.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.entities.Chat
import com.main.core.communication.Communication

interface ChatsCommunication : ObserveChatsCommunication, ValueChatsCommunication {

    fun manageChats(chats: List<Chat>)

    fun deleteChat(chat: Chat)

    fun manageMotionToastError(error: String)

    class Base(
        private val chatsChatsCommunication: ChatsChatsCommunication,
        private val chatsDeleteChatCommunication: ChatsDeleteChatCommunication,
        private val chatsMotionToastCommunication: ChatsMotionToastCommunication
    ): ChatsCommunication {

        override fun manageChats(chats: List<Chat>) {
            chatsChatsCommunication.map(chats)
        }

        override fun deleteChat(chat: Chat) {
            chatsDeleteChatCommunication.map(chat)
        }

        override fun manageMotionToastError(error: String) {
            chatsMotionToastCommunication.map(error)
        }

        override fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>) {
            chatsChatsCommunication.observe(owner, observer)
        }

        override fun observeDeleteChat(owner: LifecycleOwner, observer: Observer<Chat>) {
            chatsDeleteChatCommunication.observe(owner, observer)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
            chatsMotionToastCommunication.observe(owner, observer)
        }

        override fun valueChat(): Chat? {
            return chatsDeleteChatCommunication.value()
        }
    }
}

interface ObserveChatsCommunication {

    fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>)
    fun observeDeleteChat(owner: LifecycleOwner, observer: Observer<Chat>)

    fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>)
}

interface ValueChatsCommunication {
    fun valueChat(): Chat?
}

interface ChatsChatsCommunication: Communication.Mutable<List<Chat>> {
    class Base: Communication.Post<List<Chat>>(), ChatsChatsCommunication
}

interface ChatsDeleteChatCommunication: Communication.Mutable<Chat> {
    class Base: Communication.Post<Chat>(), ChatsDeleteChatCommunication
}

interface ChatsMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), ChatsMotionToastCommunication
}