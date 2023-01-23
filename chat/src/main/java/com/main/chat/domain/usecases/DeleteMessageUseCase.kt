package com.main.chat.domain.usecases

import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.core.Resource

class DeleteMessageUseCase(
    private val manageMessageRepository: ManageMessageRepository
) {

    suspend fun execute(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        return manageMessageRepository.deleteMessage(messageCacheModel)
    }
}