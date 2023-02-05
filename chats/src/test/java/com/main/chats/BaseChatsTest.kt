package com.main.chats

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.chats.presentation.communication.ChatsCommunication
import com.main.core.DispatchersList
import com.main.core.entities.Chat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseChatsTest {

    protected class TestChatsCommunication : ChatsCommunication {
        val chats = mutableListOf<List<Chat>>()
        val motionToastError = mutableListOf<String>()
        val deleteChat = mutableListOf<Chat>()

        override fun manageChats(chats: List<Chat>) {
            this.chats.add(chats)
        }

        override fun deleteChat(chat: Chat) {
            deleteChat.add(chat)
        }

        override fun manageMotionToastError(error: String) {
            this.motionToastError.add(error)
        }

        override fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>) = Unit
        override fun observeDeleteChat(owner: LifecycleOwner, observer: Observer<Chat>) = Unit
        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) = Unit
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }
}