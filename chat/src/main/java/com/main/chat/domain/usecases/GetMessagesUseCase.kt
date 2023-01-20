package com.main.chat.domain.usecases

import com.main.chat.data.entities.Message
import com.main.chat.domain.repository.ManageMessageRepository
import com.main.core.Resource

class GetMessagesUseCase(
    private val manageMessageRepository: ManageMessageRepository
) {

    suspend fun execute(): Resource<List<Message>> {
        return manageMessageRepository.receiveMessages()
    }
}