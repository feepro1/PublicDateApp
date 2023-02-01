package com.main.likes.di.modules

import com.main.likes.data.firebase.LikesFirebaseRepository
import com.main.likes.data.firebase.UserFirebaseRepository
import com.main.likes.data.realization.LikesRepositoryImpl
import com.main.likes.data.realization.UserRepositoryImpl
import com.main.likes.domain.firebase.LikesRepository
import com.main.likes.domain.firebase.UserRepository
import dagger.Module
import dagger.Provides

@Module
class LikesDataModule {

    @Provides
    fun provideLikesRepository(
        likesFirebaseRepository: LikesFirebaseRepository
    ): LikesRepository {
        return LikesRepositoryImpl(likesFirebaseRepository)
    }

    @Provides
    fun provideUserRepository(
        userFirebaseRepository: UserFirebaseRepository
    ): UserRepository {
        return UserRepositoryImpl(userFirebaseRepository)
    }

    @Provides
    fun provideLikesFirebaseRepository(): LikesFirebaseRepository {
        return LikesFirebaseRepository.Base()
    }

    @Provides
    fun provideUserFirebaseRepository(): UserFirebaseRepository {
        return UserFirebaseRepository.Base()
    }
}