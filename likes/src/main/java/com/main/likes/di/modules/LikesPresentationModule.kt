package com.main.likes.di.modules

import com.main.core.DispatchersList
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import com.main.likes.presentation.communication.LikesCommunication
import com.main.likes.presentation.communication.LikesLikesCommunication
import com.main.likes.presentation.communication.LikesMotionToastCommunication
import com.main.likes.presentation.viewmodel.LikesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LikesPresentationModule {

    @Provides
    fun provideLikesViewModelFactory(
        getAllLikesUseCase: GetAllLikesUseCase,
        likesCommunication: LikesCommunication,
        likesNavigation: LikesNavigation,
        dispatchers: DispatchersList
    ): LikesViewModelFactory {
        return LikesViewModelFactory(
            getAllLikesUseCase = getAllLikesUseCase,
            likesCommunication = likesCommunication,
            likesNavigation = likesNavigation,
            dispatchers = dispatchers
        )
    }

    @Provides
    fun provideLikesCommunication(
        likesLikesCommunication: LikesLikesCommunication,
        likesMotionToastCommunication: LikesMotionToastCommunication
    ): LikesCommunication {
        return LikesCommunication.Base(
            likesLikesCommunication = likesLikesCommunication,
            likesMotionToastCommunication = likesMotionToastCommunication
        )
    }

    @Provides
    fun provideLikesLikesCommunication(): LikesLikesCommunication {
        return LikesLikesCommunication.Base()
    }

    @Provides
    fun provideLikesMotionToastCommunication(): LikesMotionToastCommunication {
        return LikesMotionToastCommunication.Base()
    }

    @Provides
    fun provideDispatchersList(): DispatchersList {
        return DispatchersList.Base()
    }
}