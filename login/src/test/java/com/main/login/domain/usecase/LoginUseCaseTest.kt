package com.main.login.domain.usecase

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

//todo change unit tests
class LoginUseCaseTest {

    private val loginRepository = mock<LoginRepository>()

    @Test /** if was successful login */
    fun `test successful login`() {
        Mockito.`when`(loginRepository.login()).thenReturn(Resource.Succes(data = true))
        val loginData = LoginData(
            username = "somename",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val result = loginUseCase.execute(loginData)

        Assertions.assertEquals(true, result)
    }

    @Test /** If username is shorter than 3 chars, it's too short */
    fun `test invalid login, username is too short`() {
        Mockito.`when`(loginRepository.login()).thenReturn(
            Resource.Error(exception = UsernameException(message = "Username is too short"))
        )
        val loginData = LoginData(
            username = "sq",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception.message == "Username is too short"

        Assertions.assertTrue(result)
    }

    @Test /** If username is longer than 16 chars, it's too long */
    fun `test invalid login, username is too long`() {
        Mockito.`when`(loginRepository.login()).thenReturn(
            Resource.Error(exception = UsernameException(message = "Username is too long"))
        )
        val loginData = LoginData(
            username = "Sq23qwerty12345678",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception.message == "Username is too long"

        Assertions.assertTrue(result)
    }

    @Test /** If username is wrong (was not found on the server) */
    fun `test invalid login, username was not found on server`() {
        Mockito.`when`(loginRepository.login()).thenReturn(Resource.Error(
            exception = UsernameException(message = "Username was not found"))
        )
        val loginData = LoginData(
            username = "fail username",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception.message == "Username was not found"

        Assertions.assertTrue(result)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid login, password is too short`() {
        Mockito.`when`(loginRepository.login()).thenReturn(Resource.Error(
            exception = PasswordException(message = "Password is too short"))
        )
        val loginData = LoginData(
            username = "sq312sf",
            password = "hSl1",
            email = "some@gmail.com"
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception.message == "Password is too short"

        Assertions.assertTrue(result)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid login, password does not consist a capital letter`() {
        Mockito.`when`(loginRepository.login()).thenReturn(Resource.Error(
            exception = PasswordException(message = "Password does not consist a capital letter"))
        )
        val loginData = LoginData(
            username = "sq312sff",
            password = "sel1423",
            email = "some@gmail.com"
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception.message == "Password does not consist a capital letter"

        Assertions.assertTrue(result)
    }

    //todo rename
    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid login, email is incorrect`() {
        Mockito.`when`(loginRepository.login()).thenReturn(Resource.Error(
            exception = EmailException("Email does not consist @"))
        )
        val loginData = LoginData(
            username = "sq312sf",
            password = "Hhe1312",
            email = "somegmail.com"
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception.message == "Email does not consist @"

        Assertions.assertTrue(result)
    }
}