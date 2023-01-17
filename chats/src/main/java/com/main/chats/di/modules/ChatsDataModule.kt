package com.main.chats.di.modules

import com.main.chats.data.realization.ChatsRepositoryImpl
import com.main.chats.data.realization.LikesRepositoryImpl
import com.main.chats.domain.firebase.ChatsRepository
import com.main.chats.domain.firebase.LikesRepository
import dagger.Module
import dagger.Provides

@Module
class ChatsDataModule {

    @Provides
    fun provideChatsRepository(): ChatsRepository {
        return ChatsRepositoryImpl()
    }

    @Provides
    fun provideLikesRepository(): LikesRepository {
        return LikesRepositoryImpl()
    }
}