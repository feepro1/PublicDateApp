package com.main.swaplike.domain

import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.MessageCacheModel

interface ManageMessages {

    suspend fun receiveMessagesFromFirebase(): List<MessageCacheModel>

    suspend fun deleteMessagesOnFirebase()

    suspend fun addMessagesToLocalDatabase(
        list: List<MessageCacheModel>,
        chatCacheRepository: ChatCacheRepository
    )
}