package com.main.likes.di.modules

import com.main.likes.domain.firebase.LikesRepository
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
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
    fun provideLikesNavigation(): LikesNavigation {
        return LikesNavigation.Base()
    }

}