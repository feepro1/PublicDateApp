package com.main.chats.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.base.entity.Chat
import com.main.core.communication.Communication

interface ChatsCommunication : ObserveChatsCommunication {

    fun manageChats(chats: List<Chat>)

    fun manageMotionToastError(error: String)

    class Base(
        private val chatsChatsCommunication: ChatsChatsCommunication,
        private val chatsMotionToastCommunication: ChatsMotionToastCommunication
    ): ChatsCommunication {

        override fun manageChats(chats: List<Chat>) {
            chatsChatsCommunication.map(chats)
        }

        override fun manageMotionToastError(error: String) {
            chatsMotionToastCommunication.map(error)
        }

        override fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>) {
            chatsChatsCommunication.observe(owner, observer)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
            chatsMotionToastCommunication.observe(owner, observer)
        }
    }
}

interface ObserveChatsCommunication {

    fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>)

    fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>)
}

interface ChatsChatsCommunication: Communication.Mutable<List<Chat>> {
    class Base: Communication.Post<List<Chat>>(), ChatsChatsCommunication
}

interface ChatsMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), ChatsMotionToastCommunication
}