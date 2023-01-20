package com.main.chats.domain.firebase

import com.main.chats.data.entities.Chat
import com.main.core.Resource

interface ChatsRepository {

    suspend fun getAllChats(): Resource<List<Chat>>
}