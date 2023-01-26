package com.main.likes.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.core.entities.Like
import com.main.likes.data.entities.LikeFromUser

interface LikesCommunication : ObserveLikesCommunication {

    fun manageLikes(likes: List<Like>)

    fun manageMotionToastError(error: String)

    class Base(
        private val likesLikesCommunication: LikesLikesCommunication,
        private val likesMotionToastCommunication: LikesMotionToastCommunication
    ) : LikesCommunication {

        override fun manageLikes(likes: List<Like>) {
            likesLikesCommunication.map(likes)
        }

        override fun manageMotionToastError(error: String) {
            likesMotionToastCommunication.map(error)
        }

        override fun observeLikes(owner: LifecycleOwner, observer: Observer<List<Like>>) {
            likesLikesCommunication.observe(owner, observer)
        }

        override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
            likesMotionToastCommunication.observe(owner, observer)
        }
    }
}

interface ObserveLikesCommunication {

    fun observeLikes(owner: LifecycleOwner, observer: Observer<List<Like>>)

    fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>)
}

interface LikesLikesCommunication: Communication.Mutable<List<Like>> {
    class Base: Communication.Post<List<Like>>(), LikesLikesCommunication
}

interface LikesMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), LikesMotionToastCommunication
}