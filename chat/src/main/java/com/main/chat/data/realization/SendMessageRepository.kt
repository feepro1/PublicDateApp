package com.main.chat.data.realization

import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource

interface SendMessageRepository {

    suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    class Base(
        private val chatCacheRepository: ChatCacheRepository
    ) : SendMessageRepository {

        override suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
            return chatCacheRepository.addMessage(messageCacheModel)
        }
    }
}