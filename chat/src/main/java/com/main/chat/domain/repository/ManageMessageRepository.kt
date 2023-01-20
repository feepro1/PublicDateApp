package com.main.chat.domain.repository

import com.main.chat.data.entities.Message
import com.main.core.Resource

interface ManageMessageRepository {

    suspend fun receiveMessages(): Resource<List<Message>>

    suspend fun sendMessage(message: Message): Resource<Boolean>
}