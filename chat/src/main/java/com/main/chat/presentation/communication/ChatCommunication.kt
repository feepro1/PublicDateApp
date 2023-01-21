package com.main.chat.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.chat.data.entities.Message
import com.main.chat.data.entities.User
import com.main.core.communication.Communication

interface ChatCommunication : ObserveChatCommunication, ValueChatCommunication {

    fun manageMotionToastError(error: String)

    fun manageMessages(messages: List<Message>)

    fun manageUser(user: User)

    class Base(
        private val chatMotionToastCommunication: ChatMotionToastCommunication,
        private val chatMessagesCommunication: ChatMessagesCommunication,
        private val chatUserCommunication: ChatUserCommunication
    ): ChatCommunication {

        override fun manageMotionToastError(error: String) {
            chatMotionToastCommunication.map(error)
        }

        override fun manageMessages(messages: List<Message>) {
            chatMessagesCommunication.map(messages)
        }

        override fun manageUser(user: User) {
            chatUserCommunication.map(user)
        }

        override fun observeMessages(owner: LifecycleOwner, observer: Observer<List<Message>>) {
            chatMessagesCommunication.observe(owner, observer)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
            chatMotionToastCommunication.observe(owner, observer)
        }

        override fun valueUser(): User? {
            return chatUserCommunication.value()
        }
    }
}

interface ObserveChatCommunication {

    fun observeMessages(owner: LifecycleOwner, observer: Observer<List<Message>>)

    fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>)
}

interface ValueChatCommunication {

    fun valueUser(): User?
}

interface ChatMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), ChatMotionToastCommunication
}

interface ChatMessagesCommunication: Communication.Mutable<List<Message>> {
    class Base: Communication.Post<List<Message>>(), ChatMessagesCommunication
}

interface ChatUserCommunication: Communication.Mutable<User> {
    class Base: Communication.Post<User>(), ChatUserCommunication
}