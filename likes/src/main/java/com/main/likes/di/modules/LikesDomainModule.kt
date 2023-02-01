package com.main.likes.di.modules

import com.main.likes.domain.firebase.LikesRepository
import com.main.likes.domain.firebase.UserRepository
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import com.main.likes.domain.usecases.GetCurrentUserUseCase
import com.main.likes.domain.usecases.LikeUserUseCase
import dagger.Module
import dagger.Provides

@Module
class LikesDomainModule {

    @Provides
    fun provideGetAllLikesUseCase(
        likesRepository: LikesRepository
    ): GetAllLikesUseCase {
        return GetAllLikesUseCase(likesRepository)
    }

    @Provides
    fun provideLikeUserUseCase(
        likesRepository: LikesRepository
    ): LikeUserUseCase {
        return LikeUserUseCase(likesRepository)
    }

    @Provides
    fun provideGetCurrentUserUseCase(
        userRepository: UserRepository
    ): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(userRepository)
    }

    @Provides
    fun provideLikesNavigation(): LikesNavigation {
        return LikesNavigation.Base()
    }

}