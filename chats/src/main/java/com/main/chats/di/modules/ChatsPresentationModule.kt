package com.main.chats.di.modules

import com.main.chats.domain.navigation.ChatsNavigation
import com.main.chats.domain.usecases.DeleteChatUseCase
import com.main.chats.domain.usecases.GetAllChatsUseCase
import com.main.chats.presentation.communication.ChatsChatsCommunication
import com.main.chats.presentation.communication.ChatsCommunication
import com.main.chats.presentation.communication.ChatsDeleteChatCommunication
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
        deleteChatUseCase: DeleteChatUseCase,
        chatsCommunication: ChatsCommunication,
        chatsNavigation: ChatsNavigation,
        dispatchers: DispatchersList
    ): ChatsViewModelFactory {
        return ChatsViewModelFactory(
            getAllChatsUseCase = getAllChatsUseCase,
            deleteChatUseCase = deleteChatUseCase,
            chatsCommunication = chatsCommunication,
            chatsNavigation = chatsNavigation,
            dispatchers = dispatchers
        )
    }

    @Provides
    fun provideChatsCommunication(
        chatsChatsCommunication: ChatsChatsCommunication,
        chatsDeleteChatCommunication: ChatsDeleteChatCommunication,
        chatsMotionToastCommunication: ChatsMotionToastCommunication
    ): ChatsCommunication {
        return ChatsCommunication.Base(
            chatsChatsCommunication = chatsChatsCommunication,
            chatsDeleteChatCommunication = chatsDeleteChatCommunication,
            chatsMotionToastCommunication = chatsMotionToastCommunication
        )
    }

    @Provides
    fun provideChatsChatsCommunication(): ChatsChatsCommunication {
        return ChatsChatsCommunication.Base()
    }

    @Provides
    fun provideChatsDeleteChatCommunication(): ChatsDeleteChatCommunication {
        return ChatsDeleteChatCommunication.Base()
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