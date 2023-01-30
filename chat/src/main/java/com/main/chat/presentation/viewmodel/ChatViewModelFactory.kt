package com.main.chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.DeleteMessageUseCase
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.chat.presentation.communication.ChatCommunication
import com.main.core.DispatchersList

class ChatViewModelFactory(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val chatCommunication: ChatCommunication,
    private val chatNavigation: ChatNavigation,
    private val dispatchers: DispatchersList,
    private val chatCacheRepository: ChatCacheRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(
            getMessagesUseCase = getMessagesUseCase,
            sendMessageUseCase = sendMessageUseCase,
            deleteMessageUseCase = deleteMessageUseCase,
            chatCommunication = chatCommunication,
            chatNavigation = chatNavigation,
            dispatchers = dispatchers,
            chatCacheRepository = chatCacheRepository
        ) as T
    }
}