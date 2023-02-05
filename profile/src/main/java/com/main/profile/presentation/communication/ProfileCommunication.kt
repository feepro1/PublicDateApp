package com.main.profile.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.core.entities.User

interface ProfileCommunication : ObserveProfileCommunications, ValueProfileCommunication {

    fun manageMotionToastText(text: String)

    fun manageUserInfo(user: User)

    class Base(
        private val profileMotionToastCommunication: ProfileMotionToastCommunication,
        private val profileUserInfoCommunication: ProfileUserInfoCommunication
    ): ProfileCommunication {

        override fun manageMotionToastText(text: String) {
            profileMotionToastCommunication.map(text)
        }

        override fun manageUserInfo(user: User) {
            profileUserInfoCommunication.map(user)
        }

        override fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>) {
            profileMotionToastCommunication.observe(owner, observer)
        }

        override fun observeUserInfo(owner: LifecycleOwner, observer: Observer<User>) {
            profileUserInfoCommunication.observe(owner, observer)
        }

        override fun valueUserInfo(): User? {
            return profileUserInfoCommunication.value()
        }
    }
}

interface ObserveProfileCommunications {

    fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>)

    fun observeUserInfo(owner: LifecycleOwner, observer: Observer<User>)
}

interface ValueProfileCommunication {
    fun valueUserInfo(): User?
}

interface ProfileMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), ProfileMotionToastCommunication
}

interface ProfileUserInfoCommunication: Communication.Mutable<User> {
    class Base: Communication.Post<User>(), ProfileUserInfoCommunication
}