package com.main.chats.domain

import com.main.core.base.entity.Chat

interface ManageChatsAdapterData {

    fun mapAll(newChats: List<Chat>)
}