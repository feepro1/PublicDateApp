package com.main.login.domain.firebase

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.login.data.entities.LoginData
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_IS_EMPTY
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_WAS_NOT_FOUND_UI
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_IS_EMPTY
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_IS_INCORRECT_UI
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_IS_TOO_SHORT
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LoginFirebaseRepositoryTest {

    private val loginFirebaseRepository = mock<LoginFirebaseRepository>()

    @Test /** If it was successful login */
    fun `test successful login`() = runBlocking {
        val loginData = LoginData(
            password = "Qwerty12345",
            email = "some@gmail.com"
        )

        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(Resource.Success(data = true))

        val result = loginFirebaseRepository.login(loginData)
        Assertions.assertEquals(true, result.data)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid login, password is too short`() = runBlocking {
        val loginData = LoginData(
            password = "hSl1",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, PasswordException(PASSWORD_IS_TOO_SHORT))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == PASSWORD_IS_TOO_SHORT

        Assertions.assertTrue(result)
    }

    @Test /** If password does not consist a capital letter */
    fun `test invalid login, password does not consist a capital letter`() = runBlocking {
        val loginData = LoginData(
            password = "sel1423",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, PasswordException(PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER

        Assertions.assertTrue(result)
    }

    @Test /** If password is incorrect */
    fun `test invalid login, password is incorrect`() = runBlocking {
        val loginData = LoginData(
            password = "Qwerty1234",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, PasswordException(PASSWORD_IS_INCORRECT_UI))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == PASSWORD_IS_INCORRECT_UI

        Assertions.assertTrue(result)
    }

    @Test /** If password is empty */
    fun `test invalid login, password is empty`() = runBlocking {
        val loginData = LoginData(
            password = "",
            email = "some@gmail.com"
        )
        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, PasswordException(PASSWORD_IS_EMPTY))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == PASSWORD_IS_EMPTY

        Assertions.assertTrue(result)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid login, email is incorrect`() = runBlocking {
        val loginData = LoginData(
            password = "Hhe1312",
            email = "somegmail.com"
        )
        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, exception = EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == EMAIL_ADDRESS_IS_INCORRECT_UI

        Assertions.assertTrue(result)
    }

    @Test /** If email was not found no server */
    fun `test invalid login, email was not found`() = runBlocking {
        val loginData = LoginData(
            password = "Qwerty12345",
            email = "somegmail132.com"
        )
        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, exception = EmailException(EMAIL_WAS_NOT_FOUND_UI))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == EMAIL_WAS_NOT_FOUND_UI

        Assertions.assertTrue(result)
    }

    @Test /** If email is empty */
    fun `test invalid login, email wis empty`() = runBlocking {
        val loginData = LoginData(
            password = "Qwerty12345",
            email = ""
        )
        Mockito.`when`(loginFirebaseRepository.login(loginData)).thenReturn(
            Resource.Error(false, exception = EmailException(EMAIL_IS_EMPTY))
        )
        val executeResult = loginFirebaseRepository.login(loginData)
        val result = executeResult.exception?.message == EMAIL_IS_EMPTY

        Assertions.assertTrue(result)
    }
}