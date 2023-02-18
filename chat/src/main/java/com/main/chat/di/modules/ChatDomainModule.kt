package com.main.chat.di.modules

import com.main.chat.data.storage.shared_pref.ManageSharedPreferences
import com.main.chat.domain.firebase.ManageFirebaseMessagesRepository
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.chat.domain.interactor.ChatInteractor
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.DeleteFirebaseMessagesUseCase
import com.main.chat.domain.usecases.DeleteMessageUseCase
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
    fun provideDeleteMessageUseCase(
        manageMessageRepository: ManageMessageRepository
    ): DeleteMessageUseCase {
        return DeleteMessageUseCase(manageMessageRepository)
    }

    @Provides
    fun provideDeleteFirebaseMessagesUseCase(
        manageFirebaseMessagesRepository: ManageFirebaseMessagesRepository
    ): DeleteFirebaseMessagesUseCase {
        return DeleteFirebaseMessagesUseCase(manageFirebaseMessagesRepository)
    }

    @Provides
    fun provideChatInteractor(
        sendMessageUseCase: SendMessageUseCase,
        getMessagesUseCase: GetMessagesUseCase,
        deleteMessageUseCase: DeleteMessageUseCase,
        deleteFirebaseMessagesUseCase: DeleteFirebaseMessagesUseCase
    ): ChatInteractor {
        return ChatInteractor(
            sendMessageUseCase = sendMessageUseCase,
            getMessagesUseCase = getMessagesUseCase,
            deleteMessageUseCase = deleteMessageUseCase,
            deleteFirebaseMessagesUseCase = deleteFirebaseMessagesUseCase
        )
    }

    @Provides
    fun provideChatNavigation(): ChatNavigation {
        return ChatNavigation.Base()
    }

    @Provides
    fun provideManageSharePreferences(): ManageSharedPreferences {
        return ManageSharedPreferences.Base()
    }
}