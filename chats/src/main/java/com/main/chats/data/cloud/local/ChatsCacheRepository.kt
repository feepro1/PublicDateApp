package com.main.chats.data.cloud.local

import kotlinx.coroutines.flow.StateFlow

interface ChatsCacheRepository {

    fun getUserChats(): StateFlow<List<ChatCacheModel>>

}