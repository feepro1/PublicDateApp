package com.main.chat.data.realization

import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource

interface DeleteMessageRepository {

    suspend fun deleteMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    class Base(
        private val chatCacheRepository: ChatCacheRepository
    ) : DeleteMessageRepository {

        override suspend fun deleteMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
            return chatCacheRepository.deleteMessage(messageCacheModel)
        }
    }
}