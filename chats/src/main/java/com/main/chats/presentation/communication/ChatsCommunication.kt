package com.main.chats.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.chats.data.entities.Chat
import com.main.chats.data.entities.LikeFromUser
import com.main.core.communication.Communication

interface ChatsCommunication : ObserveChatsCommunication {

    fun manageChats(chats: List<Chat>)

    fun manageLikes(likes: List<LikeFromUser>)

    fun manageMotionToastError(error: String)

    class Base(
        private val chatsChatsCommunication: ChatsChatsCommunication,
        private val chatsLikesCommunication: ChatsLikesCommunication,
        private val chatsMotionToastCommunication: ChatsMotionToastCommunication
    ): ChatsCommunication {

        override fun manageChats(chats: List<Chat>) {
            chatsChatsCommunication.map(chats)
        }

        override fun manageLikes(likes: List<LikeFromUser>) {
            chatsLikesCommunication.map(likes)
        }

        override fun manageMotionToastError(error: String) {
            chatsMotionToastCommunication.map(error)
        }

        override fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>) {
            chatsChatsCommunication.observe(owner, observer)
        }

        override fun observeLikes(owner: LifecycleOwner, observer: Observer<List<LikeFromUser>>) {
            chatsLikesCommunication.observe(owner, observer)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
            chatsMotionToastCommunication.observe(owner, observer)
        }
    }
}

interface ObserveChatsCommunication {

    fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>)

    fun observeLikes(owner: LifecycleOwner, observer: Observer<List<LikeFromUser>>)

    fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>)
}

interface ChatsChatsCommunication: Communication.Mutable<List<Chat>> {
    class Base: Communication.Post<List<Chat>>(), ChatsChatsCommunication
}

interface ChatsLikesCommunication: Communication.Mutable<List<LikeFromUser>> {
    class Base: Communication.Post<List<LikeFromUser>>(), ChatsLikesCommunication
}

interface ChatsMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), ChatsMotionToastCommunication
}