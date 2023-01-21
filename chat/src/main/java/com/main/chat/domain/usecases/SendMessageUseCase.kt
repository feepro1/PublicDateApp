package com.main.chat.domain.usecases

import com.main.chat.data.entities.Message
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.core.Resource

class SendMessageUseCase(
    private val manageMessageRepository: ManageMessageRepository
) {

    suspend fun execute(message: Message): Resource<Boolean> {
        return manageMessageRepository.sendMessage(message)
    }
}