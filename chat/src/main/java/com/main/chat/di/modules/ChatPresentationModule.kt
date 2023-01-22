package com.main.chat.di.modules

import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.DeleteMessageUseCase
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.chat.presentation.communication.ChatCommunication
import com.main.chat.presentation.communication.ChatMessagesCommunication
import com.main.chat.presentation.communication.ChatMotionToastCommunication
import com.main.chat.presentation.communication.ChatUserCommunication
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
        getMessagesUseCase: GetMessagesUseCase,
        sendMessageUseCase: SendMessageUseCase,
        deleteMessageUseCase: DeleteMessageUseCase,
        chatCommunication: ChatCommunication,
        chatNavigation: ChatNavigation,
        dispatchers: DispatchersList
    ): ChatViewModelFactory {
        return ChatViewModelFactory(
            getMessagesUseCase = getMessagesUseCase,
            sendMessageUseCase = sendMessageUseCase,
            deleteMessageUseCase = deleteMessageUseCase,
            chatCommunication = chatCommunication,
            chatNavigation = chatNavigation,
            dispatchers = dispatchers
        )
    }

    @Provides
    fun provideChatCommunication(
        chatMotionToastCommunication: ChatMotionToastCommunication,
        chatMessagesCommunication: ChatMessagesCommunication,
        chatUserCommunication: ChatUserCommunication
    ): ChatCommunication {
        return ChatCommunication.Base(
            chatMotionToastCommunication = chatMotionToastCommunication,
            chatMessagesCommunication = chatMessagesCommunication,
            chatUserCommunication = chatUserCommunication
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