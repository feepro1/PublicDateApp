package com.main.chat.di.modules

import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.domain.interactor.ChatInteractor
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.DeleteMessageUseCase
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.chat.presentation.communication.*
import com.main.chat.presentation.viewmodel.ChatViewModelFactory
import com.main.core.DispatchersList
import com.main.core.communication.CoreChatCommunication
import com.main.core.communication.CoreCommunication
import com.main.core.viewmodel.CoreViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ChatPresentationModule {

    @Provides
    fun provideChatViewModelFactory(
        chatInteractor: ChatInteractor,
        chatCommunication: ChatCommunication,
        chatNavigation: ChatNavigation,
        dispatchers: DispatchersList,
        chatCacheRepository: ChatCacheRepository
    ): ChatViewModelFactory {
        return ChatViewModelFactory(
            chatInteractor = chatInteractor,
            chatCommunication = chatCommunication,
            chatNavigation = chatNavigation,
            dispatchers = dispatchers,
            chatCacheRepository = chatCacheRepository
        )
    }

    @Provides
    fun provideChatCommunication(
        chatMotionToastCommunication: ChatMotionToastCommunication,
        chatMessagesCommunication: ChatMessagesCommunication,
        chatMessagesWithoutClearCommunication: ChatMessagesWithoutClearCommunication,
        chatUserCommunication: ChatUserCommunication,
        chatMessageCommunication: ChatMessageCommunication
    ): ChatCommunication {
        return ChatCommunication.Base(
            chatMotionToastCommunication = chatMotionToastCommunication,
            chatMessagesCommunication = chatMessagesCommunication,
            chatMessagesWithoutClearCommunication = chatMessagesWithoutClearCommunication,
            chatUserCommunication = chatUserCommunication,
            chatMessageCommunication = chatMessageCommunication
        )
    }

    @Provides
    fun provideChatMotionToastCommunication(): ChatMotionToastCommunication {
        return ChatMotionToastCommunication.Base()
    }

    @Provides
    fun provideChatMessagesCommunication(): ChatMessagesCommunication {
        return ChatMessagesCommunication.Base()
    }

    @Provides
    fun provideChatUserCommunication(): ChatUserCommunication {
        return ChatUserCommunication.Base()
    }

    @Provides
    fun provideChatMessageCommunication(): ChatMessageCommunication {
        return ChatMessageCommunication.Base()
    }

    @Provides
    fun provideChatMessagesWithoutClearCommunication(): ChatMessagesWithoutClearCommunication {
        return ChatMessagesWithoutClearCommunication.Base()
    }

    @Provides
    fun provideDispatchersList(): DispatchersList {
        return DispatchersList.Base()
    }

    @Provides
    fun provideCoreViewModelFactory(
        coreCommunication: CoreCommunication
    ): CoreViewModelFactory {
        return CoreViewModelFactory(coreCommunication)
    }

    @Provides
    fun provideCoreCommunication(
        coreChatCommunication: CoreChatCommunication
    ): CoreCommunication {
        return CoreCommunication.Base(coreChatCommunication)
    }

    @Provides
    fun provideCoreChatCommunication(): CoreChatCommunication {
        return CoreChatCommunication.Base()
    }
}