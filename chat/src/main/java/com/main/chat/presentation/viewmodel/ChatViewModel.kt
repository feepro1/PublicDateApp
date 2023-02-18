package com.main.chat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.main.chat.data.entities.User
import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.interactor.ChatInteractor
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.presentation.communication.ChatCommunication
import com.main.chat.presentation.communication.ObserveChatCommunication
import com.main.chat.presentation.communication.ValueChatCommunication
import com.main.core.DispatchersList
import com.main.core.firebase.FirebaseConstants.REFERENCE_CHATS
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSAGES
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSENGERS
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatInteractor: ChatInteractor,
    private val chatCommunication: ChatCommunication,
    private val chatNavigation: ChatNavigation,
    private val dispatchers: DispatchersList,
    private val chatCacheRepository: ChatCacheRepository
) : ViewModel(), ObserveChatCommunication, ChatNavigation, ValueChatCommunication {

    fun receiveMessages() {
        viewModelScope.launch(dispatchers.io()) {
            val result = chatInteractor.getMessages()
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
        chatCommunication.manageMessage(messageCacheModel)
        viewModelScope.launch(dispatchers.io()) {
            val result = chatInteractor.sendMessage(messageCacheModel)
            if (result.data == false) {
                val exceptionMessage = result.exception?.message.toString()
                chatCommunication.manageMotionToastError(exceptionMessage)
            }
        }
    }

    fun deleteMessage(messageCacheModel: MessageCacheModel) {
        viewModelScope.launch(dispatchers.io()) {
            val result = chatInteractor.deleteMessage(messageCacheModel)
            if (result.data == false) {
                val exceptionMessage = result.exception?.message.toString()
                chatCommunication.manageMotionToastError(exceptionMessage)
            }
        }
    }

    fun receiveMessageRealtime(interlocutorUid: String) {
        viewModelScope.launch(dispatchers.io()) {
            val uid = Firebase.auth.currentUser?.uid.toString()
            val messages = mutableListOf<MessageCacheModel>()
            val task = Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid)
                .collection(REFERENCE_CHATS).document(interlocutorUid).collection(REFERENCE_MESSAGES)
            val listenerRegistration = task.addSnapshotListener { value, _ ->
                value?.documentChanges?.forEachIndexed { index, documentChange ->
                    val message = documentChange.document.toObject<MessageCacheModel>()
                    if (documentChange.type == DocumentChange.Type.ADDED) {
                        messages.add(message)
                        chatCacheRepository.addMessage(message)
                    }
                }
                chatCommunication.manageMessagesWithoutClear(messages.toList())
                messages.clear()
                chatInteractor.deleteAllMessagesFromFirebase(interlocutorUid)
            }
            chatCommunication.manageListenerRegistration(listenerRegistration)
        }
    }

    override fun navigateToChatsFragment(navController: NavController) {
        chatNavigation.navigateToChatsFragment(navController)
    }

    override fun observeMessages(owner: LifecycleOwner, observer: Observer<List<MessageCacheModel>>) {
        chatCommunication.observeMessages(owner, observer)
    }

    override fun observeMessagesWithoutClear(owner: LifecycleOwner, observer: Observer<List<MessageCacheModel>>) {
        chatCommunication.observeMessagesWithoutClear(owner, observer)
    }

    override fun observeMessage(owner: LifecycleOwner, observer: Observer<MessageCacheModel>) {
        chatCommunication.observeMessage(owner, observer)
    }

    override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
        chatCommunication.observeMotionToastError(owner, observer)
    }

    override fun valueUser() = chatCommunication.valueUser()
    override fun valueListenerRegistration() = chatCommunication.valueListenerRegistration()
}