package com.example.profile.di.modules

import com.example.profile.domain.usecases.GetUserInfoUseCase
import com.example.profile.domain.usecases.SaveUserInfoUseCase
import com.example.profile.presentation.communication.ProfileCommunication
import com.example.profile.presentation.communication.ProfileMotionToastCommunication
import com.example.profile.presentation.communication.ProfileUserInfoCommunication
import com.example.profile.presentation.viewmodel.ProfileViewModelFactory
import com.main.core.DispatchersList
import dagger.Module
import dagger.Provides

@Module
class ProfilePresentationModule {

    @Provides
    fun provideProfileViewModelFactory(
        getUserInfoUseCase: GetUserInfoUseCase,
        saveUserInfoUseCase: SaveUserInfoUseCase,
        dispatchers: DispatchersList,
        profileCommunication: ProfileCommunication
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            getUserInfoUseCase = getUserInfoUseCase,
            saveUserInfoUseCase = saveUserInfoUseCase,
            dispatchers = dispatchers,
            profileCommunication = profileCommunication
        )
    }

    @Provides
    fun provideProfileCommunication(
        profileMotionToastCommunication: ProfileMotionToastCommunication,
        profileUserInfoCommunication: ProfileUserInfoCommunication
    ): ProfileCommunication {
        return ProfileCommunication.Base(
            profileMotionToastCommunication = profileMotionToastCommunication,
            profileUserInfoCommunication = profileUserInfoCommunication
        )
    }

    @Provides
    fun provideProfileMotionToastCommunication(): ProfileMotionToastCommunication {
        return ProfileMotionToastCommunication.Base()
    }

    @Provides
    fun provideProfileUserInfoCommunication(): ProfileUserInfoCommunication {
        return ProfileUserInfoCommunication.Base()
    }

    @Provides
    fun provideDispatchersList(): DispatchersList {
        return DispatchersList.Base()
    }
}