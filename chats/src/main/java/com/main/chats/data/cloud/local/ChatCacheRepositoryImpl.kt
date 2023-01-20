package com.main.chats.data.cloud.local

import kotlinx.coroutines.flow.StateFlow

class ChatCacheRepositoryImpl(private val chatsDao: ChatsDao): ChatsCacheRepository {

    override fun getUserChats(): StateFlow<List<ChatCacheModel>> {
        return chatsDao.getUserChats()
    }
}