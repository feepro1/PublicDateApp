package com.main.profile.data.database

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.main.core.Resource
import com.main.core.exception.DefaultException
import com.main.profile.data.entities.UserInfo
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream


interface ProfileFirebaseRepository {

    suspend fun addImageToStorage(bitmap: Bitmap): UploadTask

    suspend fun saveUserInfo(userInfo: UserInfo): Resource<Boolean>

    suspend fun getUserInfo(): Resource<UserInfo>

    class Base : ProfileFirebaseRepository {

        override suspend fun addImageToStorage(bitmap: Bitmap): UploadTask {
            val uid = Firebase.auth.currentUser?.uid.toString()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Firebase.storage.getReference(REFERENCE_USERS_AVATARS).child(uid).putBytes(byteArray)
        }

        override suspend fun saveUserInfo(userInfo: UserInfo): Resource<Boolean> {
            val uid = Firebase.auth.currentUser?.uid.toString()

            val avatarUrl = Firebase.storage.getReference(REFERENCE_USERS_AVATARS).child(uid).downloadUrl
            avatarUrl.await()

            val task = Firebase.firestore.collection(REFERENCE_USERS).document(uid).set(
                userInfo.copy(avatarUrl = avatarUrl.result.toString())
            )
            return try {
                task.await()
                Resource.Success(true)
            } catch (e: Exception) {
                Resource.Error(false, DefaultException("Default"))
            }
        }

        override suspend fun getUserInfo(): Resource<UserInfo> {
            val uid = Firebase.auth.currentUser?.uid.toString()
            val task = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
            try {
                task.await()
            } catch (e: Exception) {
                return Resource.Error(UserInfo(), DefaultException("Default"))
            }
            val userInfo = task.result.toObject() ?: UserInfo()
            return Resource.Success(userInfo)
        }
    }

    companion object {
        const val REFERENCE_USERS_AVATARS = "usersAvatars"
        const val REFERENCE_USERS = "users"
    }
}