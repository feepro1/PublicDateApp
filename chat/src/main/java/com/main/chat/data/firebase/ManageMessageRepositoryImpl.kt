package com.main.chat.data.firebase

import com.main.chat.data.entities.Message
import com.main.chat.data.realization.DeleteMessageRepository
import com.main.chat.data.realization.ReceiveMessageRepository
import com.main.chat.data.realization.SendMessageRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.core.Resource

class ManageMessageRepositoryImpl(
    private val receiveMessageRepository: ReceiveMessageRepository,
    private val sendMessageRepository: SendMessageRepository,
    private val deleteMessageRepository: DeleteMessageRepository
) : ManageMessageRepository {

    override suspend fun receiveMessages(): Resource<List<Message>> {
        return receiveMessageRepository.receiveMessages()
    }

    override suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        return sendMessageRepository.sendMessage(messageCacheModel)
    }

    override suspend fun deleteMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        return deleteMessageRepository.deleteMessage(messageCacheModel)
    }
}