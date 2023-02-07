package com.main.chat.domain.firebase

import com.main.core.Resource

interface ManageFirebaseMessagesRepository {

    suspend fun deleteAllMessages(): Resource<Boolean>
}