package com.main.register.data.database

import android.graphics.Bitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.register.data.entities.RegisterData
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_BUSY
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_BUSY_UI
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.register.domain.firebase.RegisterFirebaseRepository.Companion.REFERENCE_USERS_AVATARS
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

interface FirebaseUserStorageRepository {

    suspend fun addUser(registerData: RegisterData): Resource<Boolean>

    suspend fun addImageToStorage(bitmap: Bitmap): UploadTask

    class Base(
        private val firebase: Firebase
    ) : FirebaseUserStorageRepository {

        override suspend fun addUser(registerData: RegisterData): Resource<Boolean> {
            registerData.avatar?.let { addImageToStorage(it) }?.await()
            val uid = firebase.auth.currentUser?.uid.toString()
            val avatarUrl = firebase.storage.getReference(REFERENCE_USERS_AVATARS).child(uid).downloadUrl
            avatarUrl.await()
            val addUserToDatabaseTask = firebase.firestore.collection(COLLECTION_USERS).document(uid).set(
                registerData.mapToRegisterDataForDatabase().copy(
                    avatarUrl = avatarUrl.result.toString()
                )
            )
            return try {
                addUserToDatabaseTask.await()
                Resource.Success(true)
            } catch (e: Exception) {
                when (e.message.toString()) {
                    EMAIL_ADDRESS_IS_INCORRECT -> {
                        Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
                    }
                    EMAIL_ADDRESS_IS_BUSY -> {
                        Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_BUSY_UI))
                    }
                    else -> Resource.Success(true)
                }
            }
        }

        override suspend fun addImageToStorage(bitmap: Bitmap): UploadTask {
            val uid = firebase.auth.currentUser?.uid.toString()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return firebase.storage.getReference(REFERENCE_USERS_AVATARS).child(uid).putBytes(byteArray)
        }
    }

    companion object {
        const val COLLECTION_USERS = "users"
    }
}