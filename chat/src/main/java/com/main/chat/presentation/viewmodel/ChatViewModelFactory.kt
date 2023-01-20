package com.main.chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.chat.presentation.communication.ChatCommunication
import com.main.core.DispatchersList

class ChatViewModelFactory(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val chatCommunication: ChatCommunication,
    private val chatNavigation: ChatNavigation,
    private val dispatchers: DispatchersList
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(
            getMessagesUseCase = getMessagesUseCase,
            sendMessageUseCase = sendMessageUseCase,
            chatCommunication = chatCommunication,
            chatNavigation = chatNavigation,
            dispatchers = dispatchers
        ) as T
    }
}