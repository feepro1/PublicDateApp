package com.main.chats.di.modules

import com.main.chats.domain.firebase.ChatsRepository
import com.main.chats.domain.firebase.LikesRepository
import com.main.chats.domain.navigation.ChatsNavigation
import com.main.chats.domain.usecases.GetAllChatsUseCase
import com.main.chats.domain.usecases.GetAllLikesUseCase
import dagger.Module
import dagger.Provides

@Module
class ChatsDomainModule {

    @Provides
    fun provideGetAllChatsUseCase(chatsRepository: ChatsRepository): GetAllChatsUseCase {
        return GetAllChatsUseCase(chatsRepository)
    }

    @Provides
    fun provideGetAllLikesUseCase(likesRepository: LikesRepository): GetAllLikesUseCase {
        return GetAllLikesUseCase(likesRepository)
    }

    @Provides
    fun provideChatsNavigation(): ChatsNavigation {
        return ChatsNavigation.Base()
    }
}