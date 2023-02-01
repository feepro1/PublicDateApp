package com.main.likes.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.main.core.DispatchersList
import com.main.core.entities.Like
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import com.main.likes.domain.usecases.GetCurrentUserUseCase
import com.main.likes.domain.usecases.LikeUserUseCase
import com.main.likes.presentation.communication.LikesCommunication
import com.main.likes.presentation.communication.ObserveLikesCommunication
import kotlinx.coroutines.launch

class LikesViewModel(
    private val getAllLikesUseCase: GetAllLikesUseCase,
    private val likeUserUseCase: LikeUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val likesCommunication: LikesCommunication,
    private val likesNavigation: LikesNavigation,
    private val dispatchers: DispatchersList
): ViewModel(), ObserveLikesCommunication, LikesNavigation {

    fun getAllLikes() {
        viewModelScope.launch(dispatchers.io()) {
            val result = getAllLikesUseCase.execute()
            if (result.data != null) {
                likesCommunication.manageLikes(result.data!!.likeFromAnotherUser.values.toList())
            }
            if (result.data?.likeFromAnotherUser?.isEmpty() == true) {
                likesCommunication.manageLikes(emptyList())
            }
            when (result.exception) {
                is NetworkException -> likesCommunication.manageMotionToastError(INTERNET_IS_UNAVAILABLE)
            }
        }
    }

    fun likeUser() {
        viewModelScope.launch(dispatchers.io()) {
            val result = likeUserUseCase.execute(likesCommunication.valueCurrentUser() ?: User())
            if (result.data == true) {
                //todo add user to list mutual likes
            }
            when (result.exception) {
                is NetworkException -> likesCommunication.manageMotionToastError(INTERNET_IS_UNAVAILABLE)
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch(dispatchers.io()) {
            val result = getCurrentUserUseCase.execute()
            if (result.data != null) {
                likesCommunication.manageCurrentUSer(result.data ?: User())
            }
            when (result.exception) {
                is NetworkException -> likesCommunication.manageMotionToastError(INTERNET_IS_UNAVAILABLE)
            }
        }
    }

    override fun observeLikes(owner: LifecycleOwner, observer: Observer<List<Like>>) {
         likesCommunication.observeLikes(owner, observer)
    }

    override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
        likesCommunication.observeMotionToastError(owner, observer)
    }

    override fun navigateToDatingFragment(navController: NavController) {
        likesNavigation.navigateToDatingFragment(navController)
    }

    override fun navigateToChatFragment(navController: NavController) {
        likesNavigation.navigateToChatFragment(navController)
    }
}