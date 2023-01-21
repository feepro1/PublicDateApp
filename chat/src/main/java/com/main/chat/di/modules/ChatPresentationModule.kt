package com.main.chat.di.modules

import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.chat.presentation.communication.ChatCommunication
import com.main.chat.presentation.communication.ChatMessagesCommunication
import com.main.chat.presentation.communication.ChatMotionToastCommunication
import com.main.chat.presentation.communication.ChatUserCommunication
import com.main.chat.presentation.viewmodel.ChatViewModelFactory
import com.main.core.DispatchersList
import dagger.Module
import dagger.Provides
import java.nio.file.DirectoryIteratorException

@Module
class ChatPresentationModule {

    @Provides
    fun provideChatViewModelFactory(
        getMessagesUseCase: GetMessagesUseCase,
        sendMessageUseCase: SendMessageUseCase,
        chatCommunication: ChatCommunication,
        chatNavigation: ChatNavigation,
        dispatchers: DispatchersList
    ): ChatViewModelFactory {
        return ChatViewModelFactory(
            getMessagesUseCase = getMessagesUseCase,
            sendMessageUseCase = sendMessageUseCase,
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
}