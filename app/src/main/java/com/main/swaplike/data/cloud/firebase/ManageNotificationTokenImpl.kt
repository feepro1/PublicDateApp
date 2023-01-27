package com.main.swaplike.data.cloud.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.main.chat.data.entities.notification.UserInfoForNotification
import com.main.core.entities.User
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import com.main.swaplike.domain.ManageNotificationToken

class ManageNotificationTokenImpl : ManageNotificationToken {

    override fun updateToken() {
        val uid = Firebase.auth.currentUser?.uid.toString()
        Firebase.messaging.token.addOnSuccessListener { token ->
            Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    val currentUser = documentSnapshot.toObject(User::class.java)
                    currentUser?.copy(token = token)?.let { user ->
                        Firebase.firestore.collection(REFERENCE_USERS).document(uid).set(user)
                    }
                }
        }
    }

    override fun updateToken(token: String) {
        Firebase.messaging.token.addOnSuccessListener { userToken ->
            val uid = Firebase.auth.currentUser?.uid.toString()
            Firebase.firestore.collection(REFERENCE_USERS).document(uid).get().addOnSuccessListener { documentSnapshot ->
                val currentUser = documentSnapshot.toObject(UserInfoForNotification::class.java)
                currentUser?.copy(token = userToken)?.let { user ->
                    Firebase.firestore.collection(REFERENCE_USERS).document(uid).set(user)
                }
            }
        }
    }
}