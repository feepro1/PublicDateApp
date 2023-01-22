package com.main.chat.data.storage.local

import com.main.core.Resource

interface ChatCacheRepository {

    fun addMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    fun deleteMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    fun getAllMessages(): List<MessageCacheModel>
}