package com.main.chats.data.cloud.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatsCacheRepository {

    fun getUserChats(): LiveData<List<ChatCacheModel>>

}