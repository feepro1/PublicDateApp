package com.main.register.data

import com.main.core.Resource
import com.main.register.data.entities.RegisterData
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class FirebaseUserStorageRepositoryTest {

    private val firebaseUserStorageRepository = mock<FirebaseUserStorageRepository>()

    @Test /** If it was successful operation by add user to database */
    fun `test successful add user to database`() {
        val registerData = RegisterData(
            email = "some@gmail.com", password = "Qwerty12345",
            confirmPassword = "Qwerty12345", avatar = byteArrayOf(),
            firstName = "Max", lastName = "Pit",
        )

        val expectedResult = Resource.Success(true)
        Mockito.`when`(firebaseUserStorageRepository.addUser(registerData)).thenReturn(expectedResult)

        val actualResult = firebaseUserStorageRepository.addData(registerData)

        Assertions.assertEquals(expectedResult, actualResult)
    }
}