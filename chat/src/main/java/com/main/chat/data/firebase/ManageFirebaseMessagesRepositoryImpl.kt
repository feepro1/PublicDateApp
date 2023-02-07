package com.main.chat.data.firebase

import com.main.chat.domain.firebase.ManageFirebaseMessagesRepository
import com.main.core.Resource

class ManageFirebaseMessagesRepositoryImpl : ManageFirebaseMessagesRepository {

    override suspend fun deleteAllMessages(): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}