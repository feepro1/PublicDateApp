package com.main.chats.di.modules

import com.main.chats.domain.firebase.ChatsRepository
import com.main.chats.domain.navigation.ChatsNavigation
import com.main.chats.domain.usecases.DeleteChatUseCase
import com.main.chats.domain.usecases.GetAllChatsUseCase
import dagger.Module
import dagger.Provides

@Module
class ChatsDomainModule {

    @Provides
    fun provideGetAllChatsUseCase(chatsRepository: ChatsRepository): GetAllChatsUseCase {
        return GetAllChatsUseCase(chatsRepository)
    }

    @Provides
    fun provideDeleteChatUseCase(chatsRepository: ChatsRepository): DeleteChatUseCase {
        return DeleteChatUseCase(chatsRepository)
    }

    @Provides
    fun provideChatsNavigation(): ChatsNavigation {
        return ChatsNavigation.Base()
    }
}