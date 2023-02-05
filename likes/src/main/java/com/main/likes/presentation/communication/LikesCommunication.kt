package com.main.likes.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.core.entities.Like
import com.main.core.entities.User

interface LikesCommunication : ObserveLikesCommunication, ValueLikesCommunication {

    fun manageLikes(likes: List<Like>)

    fun manageLike(like: Like)

    fun manageMotionToastError(error: String)

    fun manageCurrentUser(user: User)

    class Base(
        private val likesLikesCommunication: LikesLikesCommunication,
        private val likesLikeCommunication: LikesLikeCommunication,
        private val likesMotionToastCommunication: LikesMotionToastCommunication,
        private val likesCurrentUserCommunication: LikesCurrentUserCommunication
    ) : LikesCommunication {

        override fun manageLikes(likes: List<Like>) {
            likesLikesCommunication.map(likes)
        }

        override fun manageLike(like: Like) {
            likesLikeCommunication.map(like)
        }

        override fun manageMotionToastError(error: String) {
            likesMotionToastCommunication.map(error)
        }

        override fun manageCurrentUser(user: User) {
            likesCurrentUserCommunication.map(user)
        }

        override fun observeLikes(owner: LifecycleOwner, observer: Observer<List<Like>>) {
            likesLikesCommunication.observe(owner, observer)
        }

        override fun observeLike(owner: LifecycleOwner, observer: Observer<Like>) {
            likesLikeCommunication.observe(owner, observer)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
            likesMotionToastCommunication.observe(owner, observer)
        }

        override fun valueCurrentUser(): User? {
            return likesCurrentUserCommunication.value()
        }

        override fun valueLike(): Like? {
            return likesLikeCommunication.value()
        }
    }
}

interface ObserveLikesCommunication {

    fun observeLikes(owner: LifecycleOwner, observer: Observer<List<Like>>)

    fun observeLike(owner: LifecycleOwner, observer: Observer<Like>)

    fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>)
}

interface ValueLikesCommunication {

    fun valueCurrentUser(): User?

    fun valueLike(): Like?
}

interface ManageLikesCommunication {
    fun manageLike(like: Like)
}

interface LikesLikesCommunication: Communication.Mutable<List<Like>> {
    class Base: Communication.Post<List<Like>>(), LikesLikesCommunication
}

interface LikesLikeCommunication: Communication.Mutable<Like> {
    class Base: Communication.Post<Like>(), LikesLikeCommunication
}

interface LikesMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), LikesMotionToastCommunication
}

interface LikesCurrentUserCommunication: Communication.Mutable<User> {
    class Base: Communication.Post<User>(), LikesCurrentUserCommunication
}