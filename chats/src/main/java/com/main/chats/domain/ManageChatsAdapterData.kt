package com.main.chats.domain

import com.main.core.entities.Chat

interface ManageChatsAdapterData {

    fun mapAll(newChats: List<Chat>)

    fun remove(chat: Chat)
}