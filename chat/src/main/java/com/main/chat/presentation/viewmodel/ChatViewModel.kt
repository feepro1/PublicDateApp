package com.main.chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.chat.data.entities.Message
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.chat.presentation.communication.ChatCommunication
import com.main.core.DispatchersList
import com.main.core.exception.ApplicationException
import com.main.core.exception.NetworkException
import com.main.core.exception.UserException
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val chatCommunication: ChatCommunication,
    private val chatNavigation: ChatNavigation,
    private val dispatchers: DispatchersList
) : ViewModel() {

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
        }
    }
}