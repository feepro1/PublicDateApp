package com.main.chat.data.repository

import com.main.chat.data.firebase.repository.SendMessageFirebaseRepository
import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource

interface SendMessageRepository {

    suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    class Base(
        private val chatCacheRepository: ChatCacheRepository,
        private val sendMessageFirebaseRepository: SendMessageFirebaseRepository
    ) : SendMessageRepository {

        override suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
            chatCacheRepository.addMessage(messageCacheModel)
            return sendMessageFirebaseRepository.sendMessage(messageCacheModel)
        }
    }
}