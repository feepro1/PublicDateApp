package com.main.chat.data.storage.local

import com.main.core.Resource

class ChatCacheRepositoryImpl(private val chatDao: ChatDao): ChatCacheRepository {

    override fun addMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        chatDao.addMessage(messageCacheModel)
        return Resource.Success(true)
    }

    override fun deleteMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        chatDao.deleteMessage(messageCacheModel)
        return Resource.Success(true)
    }

    override fun deleteAllMessage(messages: List<MessageCacheModel>): Resource<Boolean> {
        chatDao.deleteAllMessages(messages)
        return Resource.Success(true)
    }

    override fun getAllMessages(): List<MessageCacheModel> {
        return chatDao.getAllMessages()
    }
}