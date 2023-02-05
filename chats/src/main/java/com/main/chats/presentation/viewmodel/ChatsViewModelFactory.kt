package com.main.chats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.chats.domain.navigation.ChatsNavigation
import com.main.chats.domain.usecases.DeleteChatUseCase
import com.main.chats.domain.usecases.GetAllChatsUseCase
import com.main.chats.presentation.communication.ChatsCommunication
import com.main.core.DispatchersList

class ChatsViewModelFactory(
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
    private val chatsCommunication: ChatsCommunication,
    private val chatsNavigation: ChatsNavigation,
    private val dispatchers: DispatchersList
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatsViewModel(
            getAllChatsUseCase = getAllChatsUseCase,
            deleteChatUseCase = deleteChatUseCase,
            chatsCommunication = chatsCommunication,
            chatsNavigation = chatsNavigation,
            dispatchers = dispatchers
        ) as T
    }
}