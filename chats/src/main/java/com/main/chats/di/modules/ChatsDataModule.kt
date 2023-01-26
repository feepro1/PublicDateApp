package com.main.chats.di.modules

import com.main.chats.data.realization.ChatsRepositoryImpl
import com.main.chats.data.storage.local.ChatsCacheRepository
import com.main.chats.data.storage.local.ChatsCacheRepositoryImpl
import com.main.chats.data.storage.local.ChatsDao
import com.main.chats.data.storage.remote.ChatsFirebaseRepository
import com.main.chats.domain.firebase.ChatsRepository
import dagger.Module
import dagger.Provides

@Module
class ChatsDataModule(
    private val chatsDao: ChatsDao
) {

    @Provides
    fun provideChatsRepository(
        chatsCacheRepository: ChatsCacheRepository,
        chatsFirebaseRepository: ChatsFirebaseRepository
    ): ChatsRepository {
        return ChatsRepositoryImpl(
            chatsCacheRepository = chatsCacheRepository,
            chatsFirebaseRepository = chatsFirebaseRepository
        )
    }

    @Provides
    fun provideChatsCacheRepository(): ChatsCacheRepository {
        return ChatsCacheRepositoryImpl(chatsDao)
    }

    @Provides
    fun provideChatsFirebaseRepository(): ChatsFirebaseRepository {
        return ChatsFirebaseRepository.Base()
    }
}