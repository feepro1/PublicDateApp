package com.main.register.domain.firebase

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.ExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.register.data.entities.RegisterData
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class RegisterFirebaseRepositoryTest {

    private val registerFirebaseRepository = mock<RegisterFirebaseRepository>()

    @Test /** If it was successful register */
    fun `test successful register`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = null,
            firstName = "Max", lastName = "Pit",
        )
        Mockito.`when`(registerFirebaseRepository.register(registerData)).thenReturn(Resource.Success(true))

        val result = registerFirebaseRepository.register(registerData)
        Assertions.assertEquals(true, result.data)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid register, email is incorrect`() = runBlocking {
        val registerData = RegisterData(
            email = "somename", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = null,
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerFirebaseRepository.register(registerData)).thenReturn(
            Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
        )
        val executeResult = registerFirebaseRepository.register(registerData)
        val result = executeResult.exception?.message == EMAIL_ADDRESS_IS_INCORRECT_UI

        Assertions.assertTrue(result)
    }
}