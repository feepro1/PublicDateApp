package com.main.chats.di.modules

import com.main.chats.domain.navigation.ChatsNavigation
import com.main.chats.domain.usecases.GetAllChatsUseCase
import com.main.chats.domain.usecases.GetAllLikesUseCase
import com.main.chats.presentation.communication.ChatsChatsCommunication
import com.main.chats.presentation.communication.ChatsCommunication
import com.main.chats.presentation.communication.ChatsLikesCommunication
import com.main.chats.presentation.communication.ChatsMotionToastCommunication
import com.main.chats.presentation.viewmodel.ChatsViewModelFactory
import com.main.core.DispatchersList
import com.main.core.communication.CoreChatCommunication
import com.main.core.communication.CoreCommunication
import com.main.core.viewmodel.CoreViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ChatsPresentationModule {

    @Provides
    fun provideChatsViewModelFactory(
        getAllChatsUseCase: GetAllChatsUseCase,
        getAllLikesUseCase: GetAllLikesUseCase,
        chatsCommunication: ChatsCommunication,
        chatsNavigation: ChatsNavigation,
        dispatchers: DispatchersList
    ): ChatsViewModelFactory {
        return ChatsViewModelFactory(
            getAllChatsUseCase = getAllChatsUseCase,
            getAllLikesUseCase = getAllLikesUseCase,
            chatsCommunication = chatsCommunication,
            chatsNavigation = chatsNavigation,
            dispatchers = dispatchers
        )
    }

    @Provides
    fun provideChatsCommunication(
        chatsChatsCommunication: ChatsChatsCommunication,
        chatsLikesCommunication: ChatsLikesCommunication,
        chatsMotionToastCommunication: ChatsMotionToastCommunication
    ): ChatsCommunication {
        return ChatsCommunication.Base(
            chatsChatsCommunication = chatsChatsCommunication,
            chatsLikesCommunication = chatsLikesCommunication,
            chatsMotionToastCommunication = chatsMotionToastCommunication
        )
    }

    @Provides
    fun provideChatsChatsCommunication(): ChatsChatsCommunication {
        return ChatsChatsCommunication.Base()
    }

    @Provides
    fun provideChatsLikesCommunication(): ChatsLikesCommunication {
        return ChatsLikesCommunication.Base()
    }

    @Provides
    fun provideChatsMotionToastCommunication(): ChatsMotionToastCommunication {
        return ChatsMotionToastCommunication.Base()
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