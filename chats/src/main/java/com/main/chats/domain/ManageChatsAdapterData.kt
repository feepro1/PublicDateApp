package com.main.chats.domain

import com.main.chats.data.entities.Chat

interface ManageChatsAdapterData {

    fun mapAll(newChats: List<Chat>)
}