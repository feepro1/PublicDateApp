package com.main.dating.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.core.DispatchersList
import com.main.dating.domain.interactor.DatingInteractor
import com.main.dating.presentation.communication.DatingCommunication

class DatingViewModelFactory(
    private val datingInteractor: DatingInteractor,
    private val datingCommunication: DatingCommunication,
    private val dispatchers: DispatchersList
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DatingViewModel(
            datingInteractor = datingInteractor,
            datingCommunication = datingCommunication,
            dispatchers = dispatchers
        ) as T
    }
}