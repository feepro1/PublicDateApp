package com.example.profile.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.profile.data.entities.UserInfo
import com.main.core.communication.Communication

interface ProfileCommunication : ObserveProfileCommunications {

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
    }
}

interface ObserveProfileCommunications {

    fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>)

    fun observeUserInfo(owner: LifecycleOwner, observer: Observer<UserInfo>)
}

interface ProfileMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), ProfileMotionToastCommunication
}

interface ProfileUserInfoCommunication: Communication.Mutable<UserInfo> {
    class Base: Communication.Post<UserInfo>(), ProfileUserInfoCommunication
}