package com.main.chat.data.realization

import com.main.chat.data.entities.Message
import com.main.core.Resource

interface ReceiveMessageRepository {

    suspend fun receiveMessages(): Resource<List<Message>>

    class Base : ReceiveMessageRepository {

        override suspend fun receiveMessages(): Resource<List<Message>> {
            TODO("Not yet implemented")
        }
    }

}