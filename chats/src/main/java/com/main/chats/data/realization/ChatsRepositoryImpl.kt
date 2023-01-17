package com.main.chats.data.realization

import com.main.chats.data.entities.Chat
import com.main.chats.domain.firebase.ChatsRepository
import com.main.core.Resource

class ChatsRepositoryImpl : ChatsRepository {

    override suspend fun getAllChats(): Resource<List<Chat>> {
        TODO("Not yet implemented")
    }
}