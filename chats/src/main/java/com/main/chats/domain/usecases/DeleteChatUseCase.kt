package com.main.chats.domain.usecases

import com.main.chats.domain.firebase.ChatsRepository
import com.main.core.Resource
import com.main.core.base.entity.Chat

class DeleteChatUseCase(
    private val chatsRepository: ChatsRepository
) {

    suspend fun execute(chat: Chat): Resource<Boolean> {
        return chatsRepository.deleteChat(chat)
    }

}