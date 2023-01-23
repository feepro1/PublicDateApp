package com.main.chat.domain.usecases

import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.core.Resource

class GetMessagesUseCase(
    private val manageMessageRepository: ManageMessageRepository
) {

    suspend fun execute(): Resource<List<MessageCacheModel>> {
        return manageMessageRepository.receiveMessages()
    }
}