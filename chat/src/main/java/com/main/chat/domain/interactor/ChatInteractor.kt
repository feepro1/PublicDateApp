package com.main.chat.domain.interactor

import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.usecases.DeleteMessageUseCase
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.core.Resource

class ChatInteractor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase
) {

    suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        return sendMessageUseCase.execute(messageCacheModel)
    }

    suspend fun getMessage(): Resource<List<MessageCacheModel>> {
        return getMessagesUseCase.execute()
    }

    suspend fun deleteMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
        return deleteMessageUseCase.execute(messageCacheModel)
    }
}