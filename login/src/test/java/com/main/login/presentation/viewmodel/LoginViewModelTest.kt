package com.main.login.presentation.viewmodel

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.core.state.InputTextState
import com.main.login.BaseLoginTest
import com.main.login.data.entities.LoginData
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_IS_EMPTY
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_WAS_NOT_FOUND_UI
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_IS_EMPTY
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_IS_INCORRECT_UI
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_IS_TOO_SHORT
import com.main.login.domain.navigation.LoginNavigation
import com.main.login.domain.repository.LoginRepository
import com.main.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LoginViewModelTest: BaseLoginTest() {

    private val loginRepository = mock<LoginRepository>()
    private val loginUseCase = LoginUseCase(loginRepository)
    private val loginCommunication = TestLoginCommunication()
    private val loginViewModel = LoginViewModel(
        loginUseCase = loginUseCase,
        loginCommunication = loginCommunication,
        dispatchers = TestDispatchersList(),
        loginNavigation = LoginNavigation.Base()
    )

    @BeforeEach
    fun setUp() {
        loginCommunication.emailError.clear()
        loginCommunication.passwordError.clear()
    }

    @Test /** If was successful login */
    fun `test successful login`() = runBlocking {
        val loginData = LoginData(
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        loginViewModel.login(loginData)

        val result = loginCommunication.emailError.isEmpty() && loginCommunication.passwordError.isEmpty()
        Assertions.assertTrue(result)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid login, password is too short`() = runBlocking {
        val loginData = LoginData(
            password = "hSl1",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError(PASSWORD_IS_TOO_SHORT)
        val resourceData = Resource.Error(false, PasswordException(PASSWORD_IS_TOO_SHORT))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If password does not consist a capital letter */
    fun `test invalid login, password does not consist a capital letter`() = runBlocking {
        val loginData = LoginData(
            password = "sel1423",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError(PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER)
        val resourceData = Resource.Error(false, PasswordException(PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If password is incorrect */
    fun `test invalid login, password is incorrect`() = runBlocking {
        val loginData = LoginData(
            password = "Qwerty1234",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError(PASSWORD_IS_INCORRECT_UI)
        val resourceData = Resource.Error(false, PasswordException(PASSWORD_IS_INCORRECT_UI))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If password is empty */
    fun `test invalid login, password is empty`() = runBlocking {
        val loginData = LoginData(
            password = "",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError(PASSWORD_IS_EMPTY)
        val resourceData = Resource.Error(false, PasswordException(PASSWORD_IS_EMPTY))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid login, email is incorrect`() = runBlocking {
        val loginData = LoginData(
            password = "Hhe1312",
            email = "somegmail.com"
        )
        val expected = InputTextState.ShowError(EMAIL_ADDRESS_IS_INCORRECT_UI)
        val resourceData = Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.emailError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If email was not found no server */
    fun `test invalid login, email was not found`() = runBlocking {
        val loginData = LoginData(
            password = "Qwerty12345",
            email = "somegmail132.com"
        )
        val expected = InputTextState.ShowError(EMAIL_WAS_NOT_FOUND_UI)
        val resourceData = Resource.Error(false, EmailException(EMAIL_WAS_NOT_FOUND_UI))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.emailError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If email is empty */
    fun `test invalid login, email wis empty`() = runBlocking {
        val loginData = LoginData(
            password = "Qwerty12345",
            email = ""
        )
        val expected = InputTextState.ShowError(EMAIL_IS_EMPTY)
        val resourceData = Resource.Error(false, EmailException(EMAIL_IS_EMPTY))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.emailError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }
}