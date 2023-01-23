package com.main.chat.data.repository

import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource

interface ReceiveMessageRepository {

    suspend fun receiveMessages(): Resource<List<MessageCacheModel>>

    class Base(
        private val chatCacheRepository: ChatCacheRepository
    ) : ReceiveMessageRepository {

        override suspend fun receiveMessages(): Resource<List<MessageCacheModel>> {
            return Resource.Success(chatCacheRepository.getAllMessages())
        }
    }

}