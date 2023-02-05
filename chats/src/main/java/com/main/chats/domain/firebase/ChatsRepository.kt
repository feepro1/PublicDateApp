package com.main.chats.domain.firebase

import com.main.core.Resource
import com.main.core.entities.Chat

interface ChatsRepository {

    suspend fun getAllChats(): Resource<List<Chat>>

    suspend fun deleteChat(chatChat: Chat): Resource<Boolean>
}