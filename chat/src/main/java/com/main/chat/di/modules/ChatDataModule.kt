package com.main.chat.di.modules

import com.main.chat.data.firebase.ManageMessageRepositoryImpl
import com.main.chat.data.firebase.repository.SendMessageFirebaseRepository
import com.main.chat.data.repository.DeleteMessageRepository
import com.main.chat.data.repository.ReceiveMessageRepository
import com.main.chat.data.repository.SendMessageRepository
import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.ChatCacheRepositoryImpl
import com.main.chat.data.storage.local.ChatDao
import com.main.chat.domain.firebase.ManageMessageRepository
import dagger.Module
import dagger.Provides

@Module
class ChatDataModule(
    private val chatDao: ChatDao
) {

    @Provides
    fun provideManageMessageRepository(
        receiveMessageRepository: ReceiveMessageRepository,
        sendMessageRepository: SendMessageRepository,
        deleteMessageRepository: DeleteMessageRepository
    ): ManageMessageRepository {
        return ManageMessageRepositoryImpl(
            receiveMessageRepository = receiveMessageRepository,
            sendMessageRepository = sendMessageRepository,
            deleteMessageRepository = deleteMessageRepository
        )
    }

    @Provides
    fun provideReceiveMessageRepository(
        chatCacheRepository: ChatCacheRepository
    ): ReceiveMessageRepository {
        return ReceiveMessageRepository.Base(chatCacheRepository)
    }

    @Provides
    fun provideSendMessageRepository(
        chatCacheRepository: ChatCacheRepository,
        sendMessageFirebaseRepository: SendMessageFirebaseRepository
    ): SendMessageRepository {
        return SendMessageRepository.Base(
            chatCacheRepository = chatCacheRepository,
            sendMessageFirebaseRepository = sendMessageFirebaseRepository
        )
    }

    @Provides
    fun provideDeleteMessageRepository(
        chatCacheRepository: ChatCacheRepository
    ): DeleteMessageRepository {
        return DeleteMessageRepository.Base(chatCacheRepository)
    }

    @Provides
    fun provideChatCacheRepository(): ChatCacheRepository {
        return ChatCacheRepositoryImpl(chatDao)
    }

    @Provides
    fun provideSendMessageFirebaseRepository(): SendMessageFirebaseRepository {
        return SendMessageFirebaseRepository.Base()
    }
}