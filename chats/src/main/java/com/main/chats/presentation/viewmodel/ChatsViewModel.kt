package com.main.chats.presentation.viewmodel

import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.main.chats.data.entities.Chat
import com.main.chats.data.entities.LikeFromUser
import com.main.chats.data.exception.messages.ChatsExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.chats.domain.navigation.ChatsNavigation
import com.main.chats.domain.usecases.GetAllChatsUseCase
import com.main.chats.domain.usecases.GetAllLikesUseCase
import com.main.chats.presentation.communication.ChatsCommunication
import com.main.chats.presentation.communication.ObserveChatsCommunication
import com.main.core.DispatchersList
import com.main.core.exception.NetworkException
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val getAllLikesUseCase: GetAllLikesUseCase,
    private val chatsCommunication: ChatsCommunication,
    private val chatsNavigation: ChatsNavigation,
    private val dispatchers: DispatchersList
) : ViewModel(), ObserveChatsCommunication {

    fun getAllChats() {
        viewModelScope.launch(dispatchers.io()) {
            val result = getAllChatsUseCase.execute()
            if (result.data != null) {
                chatsCommunication.manageChats(result.data ?: emptyList())
            }
            if (result.data?.isEmpty() == true) {
                chatsCommunication.manageChats(emptyList())
            }
            when (result.exception) {
                is NetworkException -> chatsCommunication.manageMotionToastError(INTERNET_IS_UNAVAILABLE)
            }
        }
    }

    fun getAllLikes() {
        viewModelScope.launch(dispatchers.io()) {
            val result = getAllLikesUseCase.execute()
            if (result.data != null) {
                chatsCommunication.manageLikes(result.data ?: emptyList())
            }
            if (result.data?.isEmpty() == true) {
                chatsCommunication.manageLikes(emptyList())
            }
            when (result.exception) {
                is NetworkException -> chatsCommunication.manageMotionToastError(INTERNET_IS_UNAVAILABLE)
            }
        }
    }

    fun navigateToChat(navController: NavController) {
        chatsNavigation.navigateToChatFragment(navController)
    }

    fun manageMenuItem(menuItem: MenuItem, navController: NavController): Boolean {
        when (menuItem.itemId) {
            com.main.core.R.id.itemDating -> chatsNavigation.navigateToDatingFragment(navController)
            com.main.core.R.id.itemProfile -> chatsNavigation.navigateToProfileFragment(navController)
        }
        return true
    }

    override fun observeChats(owner: LifecycleOwner, observer: Observer<List<Chat>>) {
        chatsCommunication.observeChats(owner, observer)
    }

    override fun observeLikes(owner: LifecycleOwner, observer: Observer<List<LikeFromUser>>) {
        chatsCommunication.observeLikes(owner, observer)
    }

    override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
        chatsCommunication.observeMotionToastError(owner, observer)
    }
}