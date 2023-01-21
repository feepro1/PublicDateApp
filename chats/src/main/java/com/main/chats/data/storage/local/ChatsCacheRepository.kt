package com.main.chats.data.storage.local

import androidx.lifecycle.LiveData

interface ChatsCacheRepository {

    fun getUserChats(): LiveData<List<ChatsCacheModel>>

}