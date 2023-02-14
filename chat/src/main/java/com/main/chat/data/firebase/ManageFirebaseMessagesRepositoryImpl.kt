package com.main.chat.data.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.chat.domain.firebase.ManageFirebaseMessagesRepository
import com.main.core.Resource
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.core.firebase.FirebaseConstants.REFERENCE_CHATS
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSAGES
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSENGERS

class ManageFirebaseMessagesRepositoryImpl : ManageFirebaseMessagesRepository {

    override fun deleteAllMessages(interlocutorUid: String): Resource<Boolean> {
        val uid = Firebase.auth.currentUser?.uid.toString()
        val task = Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid).collection(REFERENCE_CHATS)
            .document(interlocutorUid).collection(REFERENCE_MESSAGES).get()
        task.addOnSuccessListener { querySnapshot ->
            querySnapshot.documents.forEach { documentSnapshot ->
                Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid).collection(REFERENCE_CHATS)
                    .document(interlocutorUid).collection(REFERENCE_MESSAGES).document(documentSnapshot.id).delete()
            }
        }
        return if (task.isSuccessful) {
            Resource.Success(true)
        } else {
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        }
    }
}