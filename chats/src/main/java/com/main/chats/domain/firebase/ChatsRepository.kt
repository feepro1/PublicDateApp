package com.main.chats.domain.firebase

import com.main.core.Resource
import com.main.core.base.entity.Chat

interface ChatsRepository {

    suspend fun getAllChats(): Resource<List<Chat>>
}