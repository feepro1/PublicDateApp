package com.main.login.presentation.viewmodel

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.core.exception.UsernameException
import com.main.core.state.InputTextState
import com.main.login.BaseLoginTest
import com.main.login.data.entities.LoginData
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
        dispatchers = TestDispatchersList()
    )

    @BeforeEach
    fun setUp() {
        loginCommunication.emailError.clear()
        loginCommunication.passwordError.clear()
        loginCommunication.usernameError.clear()
    }

    @Test /** If was successful login */
    fun `test successful login`() = runBlocking {
        val loginData = LoginData(
            username = "somename",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        loginViewModel.login(loginData)

        val result = loginCommunication.emailError.isEmpty() &&
                loginCommunication.passwordError.isEmpty() &&
                loginCommunication.usernameError.isEmpty()
        Assertions.assertTrue(result)
    }

    @Test /** If username is shorter than 3 chars, it's too short */
    fun `test invalid login, username is too short`() = runBlocking {
        val loginData = LoginData(
            username = "sq",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError("Username is too short")
        val resourceData = Resource.Error(false, UsernameException("Username is too short"))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.usernameError[0]
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If username is longer than 16 chars, it's too long */
    fun `test invalid login, username is too long`() = runBlocking {
        val loginData = LoginData(
            username = "Sq23qwerty12345678",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError("Username is too long")
        val resourceData = Resource.Error(false, UsernameException("Username is too long"))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.usernameError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If username is wrong (was not found on the server) */
    fun `test invalid login, username was not found on server`() = runBlocking {
        val loginData = LoginData(
            username = "fail username",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError("Username was not found")
        val resourceData = Resource.Error(false, UsernameException("Username was not found"))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.usernameError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid login, password is too short`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sf",
            password = "hSl1",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError("Password is too short")
        val resourceData = Resource.Error(false, PasswordException("Password is too short"))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If password does not consist a capital letter */
    fun `test invalid login, password does not consist a capital letter`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sff",
            password = "sel1423",
            email = "some@gmail.com"
        )
        val expected = InputTextState.ShowError("Password does not consist a capital letter")
        val resourceData = Resource.Error(false, PasswordException("Password does not consist a capital letter"))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.passwordError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid login, email is incorrect`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sf",
            password = "Hhe1312",
            email = "somegmail.com"
        )
        val expected = InputTextState.ShowError("Email does not consist '@'")
        val resourceData = Resource.Error(false, EmailException("Email does not consist '@'"))

        Mockito.`when`(loginRepository.login(loginData)).thenReturn(resourceData)
        loginViewModel.login(loginData)

        val actual = loginCommunication.emailError.first()
        Assertions.assertEquals(expected.errorMessage, actual.errorMessage)
    }
}