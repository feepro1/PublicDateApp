package com.main.register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.core.dispatchers.DispatchersList
import com.main.register.data.entities.RegisterData
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.usecase.RegisterUseCase
import com.main.register.presentation.communication.RegisterCommunication
import kotlinx.coroutines.launch

class RegisterViewModel(
    val registerUseCase: RegisterUseCase,
    val registerCommunication: RegisterCommunication,
    val dispatchers: DispatchersList,
    val registerNavigation: RegisterNavigation
) : ViewModel() {

    fun register(registerData: RegisterData) {
//        viewModelScope.launch {
//            registerUseCase.execute(registerData)
//        }
    }

}