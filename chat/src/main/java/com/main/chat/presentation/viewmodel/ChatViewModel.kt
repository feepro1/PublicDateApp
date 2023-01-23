package com.main.chat.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.DeleteMessageUseCase
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.chat.presentation.communication.ChatCommunication
import com.main.chat.presentation.communication.ObserveChatCommunication
import com.main.core.DispatchersList
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val chatCommunication: ChatCommunication,
    private val chatNavigation: ChatNavigation,
    private val dispatchers: DispatchersList
) : ViewModel(), ObserveChatCommunication {

    fun receiveMessages() {
        viewModelScope.launch(dispatchers.io()) {
            val result = getMessagesUseCase.execute()
            if (result.data != null) {
                chatCommunication.manageMessages(result.data ?: emptyList())
            }
            if (result.data == null) {
                val exceptionMessage = result.exception?.message.toString()
                chatCommunication.manageMotionToastError(exceptionMessage)
            }
        }
    }

    fun sendMessage(messageCacheModel: MessageCacheModel) {
        viewModelScope.launch(dispatchers.io()) {
            val result = sendMessageUseCase.execute(messageCacheModel)
            if (result.data == false) {
                val exceptionMessage = result.exception?.message.toString()
                chatCommunication.manageMotionToastError(exceptionMessage)
            }
            if (result.data == true) {
                chatCommunication.manageMessage(messageCacheModel)
            }
        }
    }

    fun deleteMessage(messageCacheModel: MessageCacheModel) {
        viewModelScope.launch(dispatchers.io()) {
            val result = deleteMessageUseCase.execute(messageCacheModel)
            if (result.data == false) {
                val exceptionMessage = result.exception?.message.toString()
                chatCommunication.manageMotionToastError(exceptionMessage)
            }
        }
    }

    fun navigateToChatsFragment(navController: NavController) {
        chatNavigation.navigateToChatsFragment(navController)
    }

    override fun observeMessages(owner: LifecycleOwner, observer: Observer<List<MessageCacheModel>>) {
        chatCommunication.observeMessages(owner, observer)
    }

    override fun observeMessage(owner: LifecycleOwner, observer: Observer<MessageCacheModel>) {
        chatCommunication.observeMessage(owner, observer)
    }

    override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
        chatCommunication.observeMotionToastError(owner, observer)
    }
}