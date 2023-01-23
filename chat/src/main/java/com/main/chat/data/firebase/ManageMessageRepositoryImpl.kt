package com.main.chat.data.firebase

import com.main.chat.data.repository.DeleteMessageRepository
import com.main.chat.data.repository.ReceiveMessageRepository
import com.main.chat.data.repository.SendMessageRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.core.Resource

class ManageMessageRepositoryImpl(
    private val receiveMessageRepository: ReceiveMessageRepository,
    private val sendMessageRepository: SendMessageRepository,
    private val deleteMessageRepository: DeleteMessageRepository
) : ManageMessageRepository {

    override suspend fun receiveMessages(): Resource<List<MessageCacheModel>> {
        return receiveMessageRepository.receiveMessages()
    }

    override suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        return sendMessageRepository.sendMessage(messageCacheModel)
    }

    override suspend fun deleteMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        return deleteMessageRepository.deleteMessage(messageCacheModel)
    }
}