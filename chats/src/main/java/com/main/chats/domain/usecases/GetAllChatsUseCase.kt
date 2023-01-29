package com.main.chats.domain.usecases

import com.main.chats.domain.firebase.ChatsRepository
import com.main.core.Resource
import com.main.core.base.entity.Chat

class GetAllChatsUseCase(
    private val chatsRepository: ChatsRepository
) {

    suspend fun execute(): Resource<List<Chat>> {
        return chatsRepository.getAllChats()
    }
}