package com.main.chats.data.storage.local

import androidx.lifecycle.LiveData

class ChatsCacheRepositoryImpl(private val chatsDao: ChatsDao): ChatsCacheRepository {

    override fun getUserChats(): LiveData<List<ChatsCacheModel>> {
        return chatsDao.getUserChats()
    }
}