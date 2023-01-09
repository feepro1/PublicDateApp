package com.main.register.presentation.viewmodel

import com.main.core.Resource
import com.main.core.exception.*
import com.main.core.state.InputTextState
import com.main.register.BaseRegisterTest
import com.main.register.data.entities.RegisterData
import com.main.register.data.exception.message.RegisterExceptionMessages.CONFIRM_PASSWORD_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.FIRST_NAME_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.LAST_NAME_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORDS_DO_NOT_MATCH
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_IS_TOO_SHORT
import com.main.register.data.validation.ValidateStartRegisterData
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.repository.RegisterRepository
import com.main.register.domain.usecase.RegisterUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

class RegisterViewModelTest : BaseRegisterTest() {

    private val registerRepository = mock<RegisterRepository>()
    private val registerUseCase = RegisterUseCase(registerRepository)
    private val registerCommunication = TestRegisterCommunication()
    private val registerViewModel = RegisterViewModel(
        registerUseCase = registerUseCase,
        registerCommunication = registerCommunication,
        dispatchers = TestDispatchersList(),
        registerNavigation = RegisterNavigation.Base(),
        validateStartRegisterData = ValidateStartRegisterData.Base()
    )

    @BeforeEach
    fun setUp() {
        registerCommunication.emailError.clear()
        registerCommunication.passwordError.clear()
        registerCommunication.confirmPasswordError.clear()
        registerCommunication.firstNameError.clear()
        registerCommunication.lastNameError.clear()
    }

    @Test /** If was successful register */
    fun `test successful login`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatarUrl = "",
            firstName = "Max", lastName = "Pit",
        )

        registerViewModel.register(registerData)

        val result = registerCommunication.emailError.isEmpty() &&
                registerCommunication.passwordError.isEmpty()
        Assertions.assertTrue(result)
    }

    @Test /** If email is empty */
    fun `test invalid register, email is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatarUrl = "",
            firstName = "Max", lastName = "Pit",
        )

        val expected = InputTextState.ShowError(EMAIL_IS_EMPTY)
        val resourceData = Resource.Error(false, EmailException(EMAIL_IS_EMPTY))

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.validStartRegisterData((registerData))

        val actual = registerCommunication.emailError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid register, email is incorrect`() = runBlocking {
        val registerData = RegisterData(
            email = "somename", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatarUrl = "",
            firstName = "Max", lastName = "Pit",
        )
        val expected = InputTextState.ShowError(EMAIL_ADDRESS_IS_INCORRECT_UI)
        val resourceData = Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.validStartRegisterData(registerData)

        val actual = registerCommunication.emailError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If password is empty */
    fun `test invalid register, password is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "",
            confirmPassword = "Qwerty12345", avatarUrl = "",
            firstName = "Max", lastName = "Pit",
        )
        val expected = InputTextState.ShowError(PASSWORD_IS_EMPTY)
        val resourceData = Resource.Error(false, PasswordException(PASSWORD_IS_EMPTY))

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.validStartRegisterData(registerData)

        val actual = registerCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If confirm password is empty */
    fun `test invalid register, confirm password is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "", avatarUrl = "",
            firstName = "Max", lastName = "Pit",
        )
        val expected = InputTextState.ShowError(CONFIRM_PASSWORD_IS_EMPTY)
        val resourceData = Resource.Error(false, ConfirmPasswordException(CONFIRM_PASSWORD_IS_EMPTY))

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.validStartRegisterData(registerData)

        val actual = registerCommunication.confirmPasswordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid register, password is too short`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "hSl1",
            confirmPassword = "hSl1", avatarUrl = "",
            firstName = "Max", lastName = "Pit",
        )
        val expected = InputTextState.ShowError(PASSWORD_IS_TOO_SHORT)
        val resourceData = Resource.Error(false, PasswordException(PASSWORD_IS_TOO_SHORT))

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.validStartRegisterData(registerData)

        val actual = registerCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If password does not consist a capital letter */
    fun `test invalid register, password does not consist a capital letter`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "qwerty",
            confirmPassword = "qwerty", avatarUrl = "",
            firstName = "Max", lastName = "Pit",
        )
        val expected = InputTextState.ShowError(PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER)
        val resourceData = Resource.Error(
            false, PasswordException(PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER)
        )
        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.validStartRegisterData(registerData)

        val actual = registerCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If passwords do not match */
    fun `test invalid register, passwords do not match`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty1234", avatarUrl = "",
            firstName = "Max", lastName = "Pit",
        )
        val expected = InputTextState.ShowError(PASSWORDS_DO_NOT_MATCH)
        val resourceData = Resource.Error(false, ConfirmPasswordException(PASSWORDS_DO_NOT_MATCH))

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.validStartRegisterData(registerData)

        val actual = registerCommunication.confirmPasswordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If first name is empty */
    fun `test invalid register, first name is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatarUrl = "",
            firstName = "", lastName = "Pit",
        )
        val expected = InputTextState.ShowError(FIRST_NAME_IS_EMPTY)
        val resourceData = Resource.Error(false, FirstNameException(FIRST_NAME_IS_EMPTY))

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.register(registerData)

        val actual = registerCommunication.firstNameError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If last name is empty */
    fun `test invalid register, last name is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatarUrl = "",
            firstName = "Max", lastName = "",
        )
        val expected = InputTextState.ShowError(LAST_NAME_IS_EMPTY)
        val resourceData = Resource.Error(false, LastNameException(LAST_NAME_IS_EMPTY))

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(resourceData)
        registerViewModel.register(registerData)

        val actual = registerCommunication.lastNameError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

}