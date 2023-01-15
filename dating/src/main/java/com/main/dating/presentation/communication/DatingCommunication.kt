package com.main.dating.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.dating.data.entities.User

interface DatingCommunication : ObserveDatingCommunications, ValueDatingCommunications {

    fun manageMotionToastText(text: String)

    fun manageUsersList(list: List<User>)

    fun manageUser(user: User)

    class Base(
        private val datingMotionToastCommunication: DatingMotionToastCommunication,
        private val datingUsersCommunication: DatingUsersCommunication,
        private val datingUserCommunication: DatingUserCommunication
    ) : DatingCommunication {

        override fun manageMotionToastText(text: String) {
            datingMotionToastCommunication.map(text)
        }

        override fun manageUsersList(list: List<User>) {
            datingUsersCommunication.map(list)
        }

        override fun manageUser(user: User) {
            datingUserCommunication.map(user)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
            datingMotionToastCommunication.observe(owner, observer)
        }

        override fun observeUsersList(owner: LifecycleOwner, observer: Observer<List<User>>) {
            datingUsersCommunication.observe(owner, observer)
        }

        override fun valueUser() = datingUserCommunication.value()
    }
}

interface ObserveDatingCommunications {

    fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>)

    fun observeUsersList(owner: LifecycleOwner, observer: Observer<List<User>>)
}

interface ValueDatingCommunications {

    fun valueUser(): User?
}

interface DatingMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), DatingMotionToastCommunication
}

interface DatingUsersCommunication: Communication.Mutable<List<User>> {
    class Base: Communication.Post<List<User>>(), DatingUsersCommunication
}

interface DatingUserCommunication: Communication.Mutable<User> {
    class Base: Communication.Ui<User>(), DatingUserCommunication
}