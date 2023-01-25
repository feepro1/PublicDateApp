package com.main.chats.data.storage.local

class ChatsCacheRepositoryImpl(private val chatsDao: ChatsDao): ChatsCacheRepository {

    override fun getUserChats(): List<ChatCacheModel> {
        return chatsDao.getUserChats()
    }
}