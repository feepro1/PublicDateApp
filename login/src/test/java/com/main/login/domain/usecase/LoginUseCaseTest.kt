package com.main.login.domain.usecase

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.core.exception.UsernameException
import com.main.login.data.entities.LoginData
import com.main.login.domain.repository.LoginRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

//todo add new unit tests
class LoginUseCaseTest {

    private val loginRepository = mock<LoginRepository>()

    @Test /** If it was successful login */
    fun `test successful login`() = runBlocking {
        val loginData = LoginData(
            username = "somename",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginRepository.login(loginData)).thenReturn(Resource.Success(data = true))
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val result = loginUseCase.execute(loginData)

        Assertions.assertEquals(true, result.data)
    }

    @Test /** If username is shorter than 3 chars, it's too short */
    fun `test invalid login, username is too short`() = runBlocking {
        val loginData = LoginData(
            username = "sq",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginRepository.login(loginData)).thenReturn(
            Resource.Error(exception = UsernameException(message = "Username is too short"), data = false)
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception?.message == "Username is too short"

        Assertions.assertTrue(result)
    }

    @Test /** If username is longer than 16 chars, it's too long */
    fun `test invalid login, username is too long`() = runBlocking {
        val loginData = LoginData(
            username = "Sq23qwerty12345678",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginRepository.login(loginData)).thenReturn(
            Resource.Error(exception = UsernameException(message = "Username is too long"), data = false)
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception?.message == "Username is too long"

        Assertions.assertTrue(result)
    }

    @Test /** If username is wrong (was not found on the server) */
    fun `test invalid login, username was not found on server`() = runBlocking {
        val loginData = LoginData(
            username = "fail username",
            password = "Qwerty12345",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginRepository.login(loginData)).thenReturn(Resource.Error(
            exception = UsernameException(message = "Username was not found"), data = false)
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception?.message == "Username was not found"

        Assertions.assertTrue(result)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid login, password is too short`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sf",
            password = "hSl1",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginRepository.login(loginData)).thenReturn(Resource.Error(
            exception = PasswordException(message = "Password is too short"), data = false)
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception?.message == "Password is too short"

        Assertions.assertTrue(result)
    }

    @Test /** If password does not consist a capital letter */
    fun `test invalid login, password does not consist a capital letter`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sff",
            password = "sel1423",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginRepository.login(loginData)).thenReturn(Resource.Error(
            exception = PasswordException(message = "Password does not consist a capital letter"), data = false)
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception?.message == "Password does not consist a capital letter"

        Assertions.assertTrue(result)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid login, email is incorrect`() = runBlocking {
        val loginData = LoginData(
            username = "sq312sf",
            password = "Hhe1312",
            email = "somegmail.com"
        )
        Mockito.`when`(loginRepository.login(loginData)).thenReturn(Resource.Error(
            exception = EmailException("Email does not consist '@'"), data = false)
        )
        val loginUseCase = LoginUseCase(loginRepository = loginRepository)
        val executeResult = loginUseCase.execute(loginData)
        val result = executeResult.exception?.message == "Email does not consist '@'"

        Assertions.assertTrue(result)
    }
}