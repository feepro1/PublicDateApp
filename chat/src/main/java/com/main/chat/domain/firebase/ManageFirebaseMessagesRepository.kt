package com.main.chat.domain.firebase

import com.main.core.Resource

interface ManageFirebaseMessagesRepository {

    fun deleteAllMessages(interlocutorUid: String): Resource<Boolean>
}