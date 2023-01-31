package com.main.core.communication

import com.main.core.entities.Chat

interface CoreCommunication : ValueCoreCommunication, ManageCoreCommunication {

    class Base(
        private val coreChatCommunication: CoreChatCommunication
    ): CoreCommunication {

        override fun manageChat(chat: Chat) {
            coreChatCommunication.map(chat)
        }

        override fun valueChat(): Chat? {
            return coreChatCommunication.value()
        }
    }
}

interface ManageCoreCommunication {

    fun manageChat(chat: Chat)
}

interface ValueCoreCommunication {

    fun valueChat(): Chat?
}

interface CoreChatCommunication: Communication.Mutable<Chat> {
    class Base: Communication.Ui<Chat>(), CoreChatCommunication
}