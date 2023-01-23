package com.main.chat.domain

import com.main.chat.data.storage.local.MessageCacheModel

interface ManageChatAdapterData {

    fun mapAll(messages: List<MessageCacheModel>)

    fun map(message: MessageCacheModel)
}