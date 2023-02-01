package com.main.likes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.core.DispatchersList
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import com.main.likes.domain.usecases.GetCurrentUserUseCase
import com.main.likes.domain.usecases.LikeUserUseCase
import com.main.likes.presentation.communication.LikesCommunication

class LikesViewModelFactory(
    private val getAllLikesUseCase: GetAllLikesUseCase,
    private val likeUserUseCase: LikeUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val likesCommunication: LikesCommunication,
    private val likesNavigation: LikesNavigation,
    private val dispatchers: DispatchersList
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LikesViewModel(
            getAllLikesUseCase = getAllLikesUseCase,
            likeUserUseCase = likeUserUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            likesCommunication = likesCommunication,
            likesNavigation = likesNavigation,
            dispatchers = dispatchers
        ) as T
    }
}