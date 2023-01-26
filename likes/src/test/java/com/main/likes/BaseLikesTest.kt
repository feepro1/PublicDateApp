package com.main.likes

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.DispatchersList
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.presentation.communication.LikesCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseLikesTest {

    protected class TestLikesCommunication : LikesCommunication {
        val likes = mutableListOf<List<LikeFromUser>>()
        val motionToastError = mutableListOf<String>()

        override fun manageLikes(likes: List<LikeFromUser>) {
           this.likes.add(likes)
        }

        override fun manageMotionToastError(error: String) {
            this.motionToastError.add(error)
        }

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