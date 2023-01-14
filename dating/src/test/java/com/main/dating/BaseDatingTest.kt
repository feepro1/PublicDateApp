package com.main.dating

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.DispatchersList
import com.main.dating.data.entities.User
import com.main.dating.presentation.communication.DatingCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseDatingTest {

    protected class TestDatingCommunication : DatingCommunication {
        val motionToastError = mutableListOf<String>()
        val users = mutableListOf<User>()

        override fun manageMotionToastText(text: String) {
            motionToastError.add(text)
        }

        override fun manageUsersList(list: List<User>) {
            users.addAll(list)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) = Unit

        override fun observeUsersList(owner: LifecycleOwner, observer: Observer<List<User>>) = Unit
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }

}