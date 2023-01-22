package com.main.chat.di.modules

import com.main.chat.data.firebase.ManageMessageRepositoryImpl
import com.main.chat.data.realization.DeleteMessageRepository
import com.main.chat.data.realization.ReceiveMessageRepository
import com.main.chat.data.realization.SendMessageRepository
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
    fun provideReceiveMessageRepository(): ReceiveMessageRepository {
        return ReceiveMessageRepository.Base()
    }

    @Provides
    fun provideSendMessageRepository(
        chatCacheRepository: ChatCacheRepository
    ): SendMessageRepository {
        return SendMessageRepository.Base(chatCacheRepository)
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
}