package com.main.register.domain.usecase

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class RegisterUseCaseTest {

    private val registerRepository = mock<RegisterRepository>()
    private val registerUseCase = RegisterUseCase(registerRepository)

    @Test /** If it was successful register */
    fun `test successful register`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Success(true)
        )
        val result = registerData.execute(registerData)

        Assertions.assertTrue(result.data)
    }

    @Test /** If email is empty */
    fun `test invalid register, email is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, EmailException(EMAIL_IS_EMPTY))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == EMAIL_IS_EMPTY

        Assertions.assertTrue(result)
    }

    @Test /** If email bad format (if it does not consist '@') */
    fun `test invalid register, email is incorrect`() = runBlocking {
        val registerData = RegisterData(
            email = "", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == EMAIL_ADDRESS_IS_INCORRECT_UI

        Assertions.assertTrue(result)
    }

    @Test /** If password is empty */
    fun `test invalid register, password is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "",
            confirmPassword = "Qwerty12345", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, PasswordException(PASSWORD_IS_EMPTY))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == PASSWORD_IS_EMPTY

        Assertions.assertTrue(result)
    }

    @Test /** If password is shorter than 5 chars, it's too short */
    fun `test invalid register, password is too short`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "hSl1",
            confirmPassword = "hSl1", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, PasswordException(PASSWORD_IS_TOO_SHORT))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == PASSWORD_IS_TOO_SHORT

        Assertions.assertTrue(result)
    }

    @Test /** If password does not consist a capital letter */
    fun `test invalid register, password does not consist a capital letter`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "qwerty",
            confirmPassword = "qwerty", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, PasswordException(PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER

        Assertions.assertTrue(result)
    }

    @Test /** if passwords do not match */
    fun `test invalid register, passwords do not match`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, ConfirmPasswordException(PASSWORDS_DO_NOT_MATCH))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == PASSWORDS_DO_NOT_MATCH

        Assertions.assertTrue(result)
    }

    @Test /** If confirm password is empty */
    fun `test invalid register, confirm password is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, ConfirmPasswordException(CONFIRM_PASSWORD_IS_EMPTY))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == CONFIRM_PASSWORD_IS_EMPTY

        Assertions.assertTrue(result)
    }

    @Test /** If first name is empty */
    fun `test invalid register, first name is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = byteArrayOf(),
            firstName = "", lastName = "Pit",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, FirstNameException(FIRST_NAME_IS_EMPTY))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == FIRST_NAME_IS_EMPTY

        Assertions.assertTrue(result)
    }

    @Test /** If last name is empty */
    fun `test invalid register, last name is empty`() = runBlocking {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = byteArrayOf(),
            firstName = "Max", lastName = "",
        )

        Mockito.`when`(registerRepository.register(registerData)).thenReturn(
            Resource.Error(false, LastNameException(LAST_NAME_IS_EMPTY))
        )
        val executeResult = registerUseCase.execute(registerData)
        val result = executeResult.exception?.message == LAST_NAME_IS_EMPTY

        Assertions.assertTrue(result)
    }

}