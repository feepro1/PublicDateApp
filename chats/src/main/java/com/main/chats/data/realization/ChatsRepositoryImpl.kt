package com.main.chats.data.realization

import com.main.chats.data.storage.remote.ChatsFirebaseRepository
import com.main.chats.domain.firebase.ChatsRepository
import com.main.core.Resource
import com.main.core.entities.Chat

class ChatsRepositoryImpl(
    private val chatsFirebaseRepository: ChatsFirebaseRepository
) : ChatsRepository {

    override suspend fun getAllChats(): Resource<List<Chat>> {
        return Resource.Success(chatsFirebaseRepository.getListUsers())
    }

    override suspend fun deleteChat(chatChat: Chat): Resource<Boolean> {
        return Resource.Success(chatsFirebaseRepository.deleteChat(chatChat))
    }
}