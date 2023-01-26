package com.main.likes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.core.DispatchersList
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import com.main.likes.presentation.communication.LikesCommunication
import kotlinx.coroutines.launch

class LikesViewModel(
    private val getAllLikesUseCase: GetAllLikesUseCase,
    private val likesCommunication: LikesCommunication,
    private val likesNavigation: LikesNavigation,
    private val dispatchers: DispatchersList
): ViewModel() {

    fun getAllLikes() {
        viewModelScope.launch(dispatchers.io()) {
            val result = getAllLikesUseCase.execute()
            if (result.data != null) {
                likesCommunication.manageLikes(result.data ?: emptyList())
            }
            if (result.data?.isEmpty() == true) {
                likesCommunication.manageLikes(emptyList())
            }
            when (result.exception) {
                is NetworkException -> likesCommunication.manageMotionToastError(INTERNET_IS_UNAVAILABLE)
            }
        }
    }

}