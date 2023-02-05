package com.main.likes.di.modules

import com.main.core.DispatchersList
import com.main.core.communication.CoreChatCommunication
import com.main.core.communication.CoreCommunication
import com.main.core.viewmodel.CoreViewModelFactory
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import com.main.likes.domain.usecases.GetUserByUidUseCase
import com.main.likes.domain.usecases.LikeUserUseCase
import com.main.likes.presentation.communication.*
import com.main.likes.presentation.viewmodel.LikesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LikesPresentationModule {

    @Provides
    fun provideLikesViewModelFactory(
        getAllLikesUseCase: GetAllLikesUseCase,
        likeUserUseCase: LikeUserUseCase,
        getUserByUidUseCase: GetUserByUidUseCase,
        likesCommunication: LikesCommunication,
        likesNavigation: LikesNavigation,
        dispatchers: DispatchersList
    ): LikesViewModelFactory {
        return LikesViewModelFactory(
            getAllLikesUseCase = getAllLikesUseCase,
            likeUserUseCase = likeUserUseCase,
            getUserByUidUseCase = getUserByUidUseCase,
            likesCommunication = likesCommunication,
            likesNavigation = likesNavigation,
            dispatchers = dispatchers
        )
    }

    @Provides
    fun provideCoreViewModelFactory(
        coreCommunication: CoreCommunication
    ): CoreViewModelFactory {
        return CoreViewModelFactory(coreCommunication)
    }

    @Provides
    fun provideLikesCommunication(
        likesLikesCommunication: LikesLikesCommunication,
        likesLikeCommunication: LikesLikeCommunication,
        likesMotionToastCommunication: LikesMotionToastCommunication,
        likesCurrentUserCommunication: LikesCurrentUserCommunication
    ): LikesCommunication {
        return LikesCommunication.Base(
            likesLikesCommunication = likesLikesCommunication,
            likesLikeCommunication = likesLikeCommunication,
            likesMotionToastCommunication = likesMotionToastCommunication,
            likesCurrentUserCommunication = likesCurrentUserCommunication
        )
    }

    @Provides
    fun provideCoreCommunication(
        coreChatCommunication: CoreChatCommunication
    ): CoreCommunication {
        return CoreCommunication.Base(coreChatCommunication)
    }

    @Provides
    fun provideLikesLikesCommunication(): LikesLikesCommunication {
        return LikesLikesCommunication.Base()
    }

    @Provides
    fun provideLikesLikeCommunication(): LikesLikeCommunication {
        return LikesLikeCommunication.Base()
    }

    @Provides
    fun provideLikesMotionToastCommunication(): LikesMotionToastCommunication {
        return LikesMotionToastCommunication.Base()
    }

    @Provides
    fun provideLikesCurrentUserCommunication(): LikesCurrentUserCommunication {
        return LikesCurrentUserCommunication.Base()
    }

    @Provides
    fun provideCoreChatCommunication(): CoreChatCommunication {
        return CoreChatCommunication.Base()
    }

    @Provides
    fun provideDispatchersList(): DispatchersList {
        return DispatchersList.Base()
    }
}