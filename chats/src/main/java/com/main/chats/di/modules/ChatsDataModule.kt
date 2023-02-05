package com.main.chats.di.modules

import com.main.chats.data.realization.ChatsRepositoryImpl
import com.main.chats.data.storage.remote.ChatsFirebaseRepository
import com.main.chats.domain.firebase.ChatsRepository
import dagger.Module
import dagger.Provides

@Module
class ChatsDataModule {

    @Provides
    fun provideChatsRepository(
        chatsFirebaseRepository: ChatsFirebaseRepository
    ): ChatsRepository {
        return ChatsRepositoryImpl(
            chatsFirebaseRepository = chatsFirebaseRepository
        )
    }

    @Provides
    fun provideChatsFirebaseRepository(): ChatsFirebaseRepository {
        return ChatsFirebaseRepository.Base()
    }
}