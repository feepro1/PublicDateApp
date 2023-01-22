package com.main.chat.domain.firebase

import com.main.chat.data.entities.Message
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource

interface ManageMessageRepository {

    suspend fun receiveMessages(): Resource<List<Message>>

    suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    suspend fun deleteMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>
}