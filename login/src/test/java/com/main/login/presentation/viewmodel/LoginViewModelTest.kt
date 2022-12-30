package com.main.login.presentation.viewmodel

import com.main.core.state.InputTextState
import com.main.login.BaseLoginTest
import com.main.login.data.entities.LoginData
import com.main.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.mock

class LoginViewModelTest: BaseLoginTest() {

    private val loginUseCase = mock<LoginUseCase>()
    private val loginCommunication = TestLoginCommunication()

    val loginViewModel = LoginViewModel(
        loginUseCase = loginUseCase,
        loginCommunication = loginCommunication,
        handleLoginException = handleLoginExcepion
    )

    @BeforeEach
    fun setUp() {
        loginCommunication.emailError.clear()
        loginCommunication.passwordError.clear()
        loginCommunication.usernameError.clear()
    }

    @Test /** if was successful login */
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
        loginViewModel.login(loginData)

        val expected = InputTextState.ShowError("Username is too short")
        val actual = loginCommunication.usernameError[0]
        Assertions.assertEquals(expected, actual)
    }

    @Test /** If username is longer than 16 chars, it's too long */
    fun `test invalid login, username is too long`() = runBlocking {
        val loginData = LoginData(
            username = "Sq23qwerty12345678",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        loginViewModel.login(loginData)

        val expected = InputTextState.ShowError("Username is too long")
        val actual = loginCommunication.usernameError[0]
        Assertions.assertEquals(expected, actual)
    }

    @Test /** If username is wrong (was not found on the server) */
    fun `test invalid login, username was not found on server`() = runBlocking {
        val loginData = LoginData(
            username = "fail username",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        loginViewModel.login(loginData)

        val expected = InputTextState.ShowError("Username was not found")
        val actual = loginCommunication.usernameError[0]
        Assertions.assertEquals(expected, actual)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid login, password is too short`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sf",
            password = "hSl1",
            email = "some@gmail.com"
        )
        loginViewModel.login(loginData)

        val expected = InputTextState.ShowError("Password is too short")
        val actual = loginCommunication.passwordError[0]
        Assertions.assertEquals(expected, actual)
    }

    @Test /** If password does not consist a capital letter */
    fun `test invalid login, password does not consist a capital letter`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sff",
            password = "sel1423",
            email = "some@gmail.com"
        )
        loginViewModel.login(loginData)

        val expected = InputTextState.ShowError("Password does not consist a capital letter")
        val actual = loginCommunication.passwordError[0]
        Assertions.assertEquals(expected, actual)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid login, email is incorrect`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sf",
            password = "Hhe1312",
            email = "somegmail.com"
        )
        loginViewModel.login(loginData)

        val expected = InputTextState.ShowError("Email does not consist '@'")
        val actual = loginCommunication.emailError[0]
        Assertions.assertEquals(expected, actual)
    }
}