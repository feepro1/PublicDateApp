package com.main.likes

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.DispatchersList
import com.main.core.entities.Like
import com.main.likes.presentation.communication.LikesCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseLikesTest {

    protected class TestLikesCommunication : LikesCommunication {
        val likes = mutableListOf<List<Like>>()
        val motionToastError = mutableListOf<String>()

        override fun manageLikes(likes: List<Like>) {
            this.likes.add(likes)
        }

        override fun manageMotionToastError(error: String) {
            this.motionToastError.add(error)
        }

        override fun observeLikes(owner: LifecycleOwner, observer: Observer<List<Like>>) = Unit
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