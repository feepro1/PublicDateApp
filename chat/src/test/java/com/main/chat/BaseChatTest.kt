package com.main.chat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.chat.data.entities.Message
import com.main.chat.data.entities.User
import com.main.chat.presentation.communication.ChatCommunication
import com.main.core.DispatchersList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseChatTest {

    protected class TestChatCommunication : ChatCommunication {
        val motionToastError = mutableListOf<String>()
        val messages = mutableListOf<List<Message>>()
        val user = mutableListOf<User>()

        override fun manageMotionToastError(error: String) {
            motionToastError.add(error)
        }

        override fun manageMessages(messages: List<Message>) {
            this.messages.add(messages)
        }

        override fun manageUser(user: User) {
            this.user.add(user)
        }

        override fun observeMessages(owner: LifecycleOwner, observer: Observer<List<Message>>) = Unit
        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) = Unit
        override fun valueUser(): User = this.user.first()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }
}