package com.main.profile.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.profile.data.entities.UserInfo

interface ProfileCommunication : ObserveProfileCommunications, ValueProfileCommunication {

    fun manageMotionToastText(text: String)

    fun manageUserInfo(userInfo: UserInfo)

    class Base(
        private val profileMotionToastCommunication: ProfileMotionToastCommunication,
        private val profileUserInfoCommunication: ProfileUserInfoCommunication
    ): ProfileCommunication {

        override fun manageMotionToastText(text: String) {
            profileMotionToastCommunication.map(text)
        }

        override fun manageUserInfo(userInfo: UserInfo) {
            profileUserInfoCommunication.map(userInfo)
        }

        override fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>) {
            profileMotionToastCommunication.observe(owner, observer)
        }

        override fun observeUserInfo(owner: LifecycleOwner, observer: Observer<UserInfo>) {
            profileUserInfoCommunication.observe(owner, observer)
        }

        override fun valueUserInfo(): UserInfo? {
            return profileUserInfoCommunication.value()
        }
    }
}

interface ObserveProfileCommunications {

    fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>)

    fun observeUserInfo(owner: LifecycleOwner, observer: Observer<UserInfo>)
}

interface ValueProfileCommunication {
    fun valueUserInfo(): UserInfo?
}

interface ProfileMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), ProfileMotionToastCommunication
}

interface ProfileUserInfoCommunication: Communication.Mutable<UserInfo> {
    class Base: Communication.Post<UserInfo>(), ProfileUserInfoCommunication
}