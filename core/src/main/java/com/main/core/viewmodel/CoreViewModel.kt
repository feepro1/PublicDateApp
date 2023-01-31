package com.main.core.viewmodel

import androidx.lifecycle.ViewModel
import com.main.core.entities.Chat
import com.main.core.communication.CoreCommunication
import com.main.core.communication.ManageCoreCommunication
import com.main.core.communication.ValueCoreCommunication

class CoreViewModel(
    private val coreCommunication: CoreCommunication
) : ViewModel(), ValueCoreCommunication, ManageCoreCommunication {

    override fun manageChat(chat: Chat) {
        coreCommunication.manageChat(chat)
    }

    override fun valueChat(): Chat? {
        return coreCommunication.valueChat()
    }
}