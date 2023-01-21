package com.main.chat.data.realization

import com.main.chat.data.entities.Message
import com.main.core.Resource

interface SendMessageRepository {

    suspend fun sendMessage(message: Message): Resource<Boolean>

    class Base : SendMessageRepository {

        override suspend fun sendMessage(message: Message): Resource<Boolean> {
            TODO("Not yet implemented")
        }
    }
}