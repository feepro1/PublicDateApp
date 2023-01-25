package com.main.chats.data.storage.local

interface ChatsCacheRepository {

    fun getUserChats(): List<ChatCacheModel>

}