package com.example.profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.DispatchersList
import com.main.core.entities.User
import com.main.profile.presentation.communication.ProfileCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseProfileTest {

    protected class TestProfileCommunication : ProfileCommunication {
        val motionToastText = mutableListOf<String>()
        val userInfo = mutableListOf<User>()

        override fun manageMotionToastText(text: String) {
            motionToastText.add(text)
        }

        override fun manageUserInfo(user: User) {
            this.userInfo.add(user)
        }

        override fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>) = Unit
        override fun observeUserInfo(owner: LifecycleOwner, observer: Observer<User>) = Unit
        override fun valueUserInfo(): User = userInfo.first()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }

}