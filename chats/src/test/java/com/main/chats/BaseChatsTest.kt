package com.main.chats

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.chats.data.entities.Chat
import com.main.chats.data.entities.LikeFromUser
import com.main.chats.presentation.communication.ChatsCommunication
import com.main.core.DispatchersList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseChatsTest {

    protected class TestChatsCommunication : ChatsCommunication {
        val chats = mutableListOf<List<Chat>>()
        val likes = mutableListOf<List<LikeFromUser>>()
        val motionToastError = mutableListOf<String>()

        override fun manageChats(chats: List<Chat>) {
            this.chats.add(chats)
        }

        override fun manageLikes(likes: List<LikeFromUser>) {
            this.likes.add(likes)
        }

        override fun manageMotionToastError(error: String) {
            this.motionToastError.add(error)
        }

        override fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>) = Unit

        override fun observeLikes(owner: LifecycleOwner, observer: Observer<List<LikeFromUser>>) = Unit

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