package com.main.register.presentation.viewmodel

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.main.core.DispatchersList
import com.main.core.ManageImageRepository
import com.main.core.exception.*
import com.main.core.state.InputTextState
import com.main.core.toast.showColorToast
import com.main.register.R
import com.main.register.data.entities.RegisterData
import com.main.register.data.validation.ValidateStartRegisterData
import com.main.register.domain.exception.ManageRegisterCommunications
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.usecase.RegisterUseCase
import com.main.register.presentation.communication.ObserveRegisterCommunications
import com.main.register.presentation.communication.RegisterCommunication
import com.main.register.presentation.communication.ValueRegisterCommunications
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val registerCommunication: RegisterCommunication,
    private val dispatchers: DispatchersList,
    private val registerNavigation: RegisterNavigation,
    private val validateStartRegisterData: ValidateStartRegisterData,
    private val manageImageRepository: ManageImageRepository
) : ViewModel(), ObserveRegisterCommunications, ManageRegisterCommunications, ValueRegisterCommunications<RegisterData> {

    fun register(registerData: RegisterData, navController: NavController, showToast: () -> (Unit)) {
        viewModelScope.launch(dispatchers.io()) {
            val result = registerUseCase.execute(registerData)
            if (result.data == true) {
                withContext(dispatchers.ui()) { showToast.invoke() }
            }
            when (result.exception) {
                is FirstNameException -> {
                    registerCommunication.manageFirstNameError(InputTextState.ShowError(result.exception?.message!!))
                }
                is LastNameException -> {
                    registerCommunication.manageLastNameError(InputTextState.ShowError(result.exception?.message!!))
                }
                is EmailException -> {
                    registerCommunication.manageEmailError(InputTextState.ShowError(result.exception?.message!!))
                    withContext(dispatchers.ui()) { navController.popBackStack() }
                }
            }
        }
    }

    fun checkIsUserConfirmedEmail(navController: NavController) {
        if (Firebase.auth.currentUser != null && Firebase.auth.currentUser?.isEmailVerified == true) {
            registerNavigation.navigateToDatingFragment(navController)
        }
    }

    fun validStartRegisterData(registerData: RegisterData, navController: NavController){
        val result = validateStartRegisterData.valid(registerData)
        if (result.data == true) {
            mapRegisterData(registerData)
            registerNavigation.navigateToFinishRegisterFragment(navController)
        }
        when (result.exception) {
            is PasswordException -> {
                registerCommunication.managePasswordError(InputTextState.ShowError(result.exception?.message!!))
            }
            is EmailException -> {
                registerCommunication.manageEmailError(InputTextState.ShowError(result.exception?.message!!))
            }
            is ConfirmPasswordException -> {
                registerCommunication.manageConfirmPasswordError(InputTextState.ShowError(result.exception?.message!!))
            }
        }
    }

    fun onClickChooseImage(launcher: ActivityResultLauncher<Intent>) {
        manageImageRepository.onClickChooseImage(launcher)
    }

    fun navigateToLoginFragment(navController: NavController) {
        registerNavigation.navigateToLoginFragment(navController)
    }

    override fun observeRegisterEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterEmailError(owner, observer)

    override fun observeRegisterPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterPasswordError(owner, observer)

    override fun observeRegisterConfirmPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterConfirmPasswordError(owner, observer)

    override fun observeRegisterFirstNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterFirstNameError(owner, observer)

    override fun observeRegisterLastNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterFirstNameError(owner, observer)

    override fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>) {
        registerCommunication.observeMotionToastText(owner, observer)
    }

    override fun clearEmailError() = registerCommunication.manageEmailError(InputTextState.ClearError())

    override fun clearPasswordError() = registerCommunication.managePasswordError(InputTextState.ClearError())

    override fun clearConfirmPasswordError() = registerCommunication.manageConfirmPasswordError(InputTextState.ClearError())

    override fun clearFirstNameError() = registerCommunication.manageFirstNameError(InputTextState.ClearError())

    override fun clearLastNameError() = registerCommunication.manageLastNameError(InputTextState.ClearError())

    override fun showEmailMessage(exception: ApplicationException) =
         registerCommunication.manageEmailError(InputTextState.ShowError(exception.message!!))

    override fun showPasswordMessage(exception: ApplicationException) =
        registerCommunication.managePasswordError(InputTextState.ShowError(exception.message!!))

    override fun showConfirmPasswordMessage(exception: ApplicationException) =
        registerCommunication.manageConfirmPasswordError(InputTextState.ShowError(exception.message!!))

    override fun showFirstNameMessage(exception: ApplicationException) =
        registerCommunication.manageFirstNameError(InputTextState.ShowError(exception.message!!))

    override fun showLastNameMessage(exception: ApplicationException) =
        registerCommunication.manageLastNameError(InputTextState.ShowError(exception.message!!))

    override fun mapRegisterData(registerData: RegisterData) {
        registerCommunication.manageRegisterData(registerData = registerData)
    }

    override fun mapMotionToastText(text: String) = registerCommunication.manageMotionToastText(text)

    override fun valueRegisterData() = registerCommunication.valueRegisterData()
}