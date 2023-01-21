package com.main.chat.di.modules

import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import dagger.Module
import dagger.Provides

@Module
class ChatDomainModule {

    @Provides
    fun provideGetMessagesUseCase(
        manageMessageRepository: ManageMessageRepository
    ): GetMessagesUseCase {
        return GetMessagesUseCase(manageMessageRepository)
    }

    @Provides
    fun provideSendMessageUseCase(
        manageMessageRepository: ManageMessageRepository
    ): SendMessageUseCase {
        return SendMessageUseCase(manageMessageRepository)
    }

    @Provides
    fun provideChatNavigation(): ChatNavigation {
        return ChatNavigation.Base()
    }
}