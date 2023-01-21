package com.main.chats.data.cloud.local

import androidx.lifecycle.LiveData

class ChatCacheRepositoryImpl(private val chatsDao: ChatsDao): ChatsCacheRepository {

    override fun getUserChats(): LiveData<List<ChatCacheModel>> {
        return chatsDao.getUserChats()
    }
}