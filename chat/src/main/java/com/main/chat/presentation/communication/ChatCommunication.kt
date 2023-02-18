package com.main.chat.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.firebase.firestore.ListenerRegistration
import com.main.chat.data.entities.User
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.communication.Communication

interface ChatCommunication : ObserveChatCommunication, ValueChatCommunication {

    fun manageMotionToastError(error: String)

    fun manageMessages(messages: List<MessageCacheModel>)

    fun manageMessagesWithoutClear(messages: List<MessageCacheModel>)

    fun manageMessage(message: MessageCacheModel)

    fun manageUser(user: User)

    fun manageListenerRegistration(listenerRegistration: ListenerRegistration)

    class Base(
        private val chatMotionToastCommunication: ChatMotionToastCommunication,
        private val chatMessagesCommunication: ChatMessagesCommunication,
        private val chatMessagesWithoutClearCommunication: ChatMessagesWithoutClearCommunication,
        private val chatUserCommunication: ChatUserCommunication,
        private val chatMessageCommunication: ChatMessageCommunication,
        private val chatListenerRegistrationCommunication: ChatListenerRegistrationCommunication
    ): ChatCommunication {

        override fun manageMotionToastError(error: String) {
            chatMotionToastCommunication.map(error)
        }

        override fun manageMessages(messages: List<MessageCacheModel>) {
            chatMessagesCommunication.map(messages)
        }

        override fun manageMessagesWithoutClear(messages: List<MessageCacheModel>) {
            chatMessagesWithoutClearCommunication.map(messages)
        }

        override fun manageMessage(message: MessageCacheModel) {
            chatMessageCommunication.map(message)
        }

        override fun manageUser(user: User) {
            chatUserCommunication.map(user)
        }

        override fun manageListenerRegistration(listenerRegistration: ListenerRegistration) {
            chatListenerRegistrationCommunication.map(listenerRegistration)
        }

        override fun observeMessages(owner: LifecycleOwner, observer: Observer<List<MessageCacheModel>>) {
            chatMessagesCommunication.observe(owner, observer)
        }

        override fun observeMessagesWithoutClear(owner: LifecycleOwner, observer: Observer<List<MessageCacheModel>>) {
            chatMessagesWithoutClearCommunication.observe(owner, observer)
        }

        override fun observeMessage(owner: LifecycleOwner, observer: Observer<MessageCacheModel>) {
            chatMessageCommunication.observe(owner, observer)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
            chatMotionToastCommunication.observe(owner, observer)
        }

        override fun valueUser() = chatUserCommunication.value()
        override fun valueListenerRegistration() = chatListenerRegistrationCommunication.value()
    }
}

interface ObserveChatCommunication {

    fun observeMessages(owner: LifecycleOwner, observer: Observer<List<MessageCacheModel>>)

    fun observeMessagesWithoutClear(owner: LifecycleOwner, observer: Observer<List<MessageCacheModel>>)

    fun observeMessage(owner: LifecycleOwner, observer: Observer<MessageCacheModel>)

    fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>)
}

interface ValueChatCommunication {

    fun valueUser(): User?

    fun valueListenerRegistration(): ListenerRegistration?
}

interface ChatMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), ChatMotionToastCommunication
}

interface ChatMessagesCommunication: Communication.Mutable<List<MessageCacheModel>> {
    class Base: Communication.Post<List<MessageCacheModel>>(), ChatMessagesCommunication
}

interface ChatMessagesWithoutClearCommunication: Communication.Mutable<List<MessageCacheModel>> {
    class Base: Communication.Post<List<MessageCacheModel>>(), ChatMessagesWithoutClearCommunication
}

interface ChatUserCommunication: Communication.Mutable<User> {
    class Base: Communication.Post<User>(), ChatUserCommunication
}

interface ChatMessageCommunication: Communication.Mutable<MessageCacheModel> {
    class Base: Communication.Post<MessageCacheModel>(), ChatMessageCommunication
}

interface ChatListenerRegistrationCommunication: Communication.Mutable<ListenerRegistration> {
    class Base: Communication.Post<ListenerRegistration>(), ChatListenerRegistrationCommunication
}