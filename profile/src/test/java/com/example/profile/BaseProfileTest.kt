package com.example.profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.DispatchersList
import com.main.profile.data.entities.UserInfo
import com.main.profile.presentation.communication.ProfileCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseProfileTest {

    protected class TestProfileCommunication : ProfileCommunication {
        val motionToastText = mutableListOf<String>()
        val userInfo = mutableListOf<UserInfo>()

        override fun manageMotionToastText(text: String) {
            motionToastText.add(text)
        }

        override fun manageUserInfo(userInfo: UserInfo) {
            this.userInfo.add(userInfo)
        }

        override fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>) = Unit
        override fun observeUserInfo(owner: LifecycleOwner, observer: Observer<UserInfo>) = Unit
        override fun valueUserInfo(): UserInfo = userInfo.first()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }

}