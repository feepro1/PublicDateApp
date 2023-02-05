package com.main.login.domain.firebase

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.ExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.core.exception.ExceptionMessages.EMAIL_WAS_NOT_FOUND_UI
import com.main.core.exception.ExceptionMessages.PASSWORD_IS_INCORRECT_UI
import com.main.core.exception.PasswordException
import com.main.login.data.entities.LoginData
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LoginFirebaseRepositoryTest {

    private val loginFirebaseRepository = mock<LoginFirebaseRepository>()

    @Test /** If it was successful login */
    fun `test successful login`() = runBlocking {
        val loginData = LoginData(password = "Qwerty12345", email = "some@gmail.com")

        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(Resource.Success(true))

        val result = loginFirebaseRepository.login(loginData)
        Assertions.assertEquals(true, result.data)
    }

    @Test /** If password is incorrect */
    fun `test invalid login, password is incorrect`() = runBlocking {
        val loginData = LoginData(password = "Qwerty1234", email = "some@gmail.com")

        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, PasswordException(PASSWORD_IS_INCORRECT_UI))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == PASSWORD_IS_INCORRECT_UI

        Assertions.assertTrue(result)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid login, email is incorrect`() = runBlocking {
        val loginData = LoginData(password = "Hhe1312", email = "somegmail.com")

        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, exception = EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == EMAIL_ADDRESS_IS_INCORRECT_UI

        Assertions.assertTrue(result)
    }

    @Test /** If email was not found no server */
    fun `test invalid login, email was not found`() = runBlocking {
        val loginData = LoginData(password = "Qwerty12345", email = "somegmail132.com")

        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, exception = EmailException(EMAIL_WAS_NOT_FOUND_UI))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == EMAIL_WAS_NOT_FOUND_UI

        Assertions.assertTrue(result)
    }
}