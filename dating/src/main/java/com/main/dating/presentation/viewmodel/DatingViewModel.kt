package com.main.dating.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.core.DispatchersList
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.domain.interactor.DatingInteractor
import com.main.dating.presentation.communication.DatingCommunication
import com.main.dating.presentation.communication.ObserveDatingCommunications
import kotlinx.coroutines.launch

class DatingViewModel(
    private val datingInteractor: DatingInteractor,
    private val datingCommunication: DatingCommunication,
    private val dispatchers: DispatchersList
) : ViewModel(), ObserveDatingCommunications {

    fun getUsersFromDatabase() {
        viewModelScope.launch(dispatchers.io()) {
            val result = datingInteractor.getUsersFromDatabase()
            if (result.data != null) {
                result.data?.let { datingCommunication.manageUsersList(it) }
            }
            if (result.exception is NetworkException) {
                val message = (result.exception as NetworkException).message.toString()
                datingCommunication.manageMotionToastText(message)
            }
        }
    }

    fun likeUser(user: User) {
        viewModelScope.launch(dispatchers.io()) {
            val result = datingInteractor.likeUser(user)
            if (result.exception is NetworkException) {
                val message = (result.exception as NetworkException).message.toString()
                datingCommunication.manageMotionToastText(message)
            }
        }
    }

    fun dislikeUser(user: User) {
        viewModelScope.launch(dispatchers.io()) {
            val result = datingInteractor.dislikeUser(user)
            if (result.exception is NetworkException) {
                val message = (result.exception as NetworkException).message.toString()
                datingCommunication.manageMotionToastText(message)
            }
        }
    }

    override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
        datingCommunication.observeMotionToastError(owner, observer)
    }

    override fun observeUsersList(owner: LifecycleOwner, observer: Observer<List<User>>) {
        datingCommunication.observeUsersList(owner, observer)
    }
}