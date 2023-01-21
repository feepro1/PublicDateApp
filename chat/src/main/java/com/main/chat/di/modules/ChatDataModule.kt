package com.main.chat.di.modules

import com.main.chat.data.firebase.ManageMessageRepositoryImpl
import com.main.chat.data.realization.ReceiveMessageRepository
import com.main.chat.data.realization.SendMessageRepository
import com.main.chat.domain.firebase.ManageMessageRepository
import dagger.Module
import dagger.Provides

@Module
class ChatDataModule {

    @Provides
    fun provideManageMessageRepository(
        receiveMessageRepository: ReceiveMessageRepository,
        sendMessageRepository: SendMessageRepository
    ): ManageMessageRepository {
        return ManageMessageRepositoryImpl(
            receiveMessageRepository = receiveMessageRepository,
            sendMessageRepository = sendMessageRepository
        )
    }

    @Provides
    fun provideReceiveMessageRepository(): ReceiveMessageRepository {
        return ReceiveMessageRepository.Base()
    }

    @Provides
    fun provideSendMessageRepository(): SendMessageRepository {
        return SendMessageRepository.Base()
    }
}