package com.main.chats.data.realization

import com.main.chats.data.entities.Chat
import com.main.chats.data.storage.local.ChatsCacheRepository
import com.main.chats.data.storage.remote.ChatsFirebaseRepository
import com.main.chats.domain.firebase.ChatsRepository
import com.main.core.Resource

class ChatsRepositoryImpl(
    private val chatsCacheRepository: ChatsCacheRepository,
    private val chatsFirebaseRepository: ChatsFirebaseRepository
) : ChatsRepository {

    override suspend fun getAllChats(): Resource<List<Chat>> {
        val chatCacheModelList = chatsCacheRepository.getUserChats()
        return Resource.Success(chatsFirebaseRepository.getListUsers(chatCacheModelList))
    }
}