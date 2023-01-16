package com.main.profile.di.modules

import com.main.core.DispatchersList
import com.main.core.ManageImageRepository
import com.main.profile.domain.navigation.ProfileNavigation
import com.main.profile.domain.usecases.GetUserInfoUseCase
import com.main.profile.domain.usecases.SaveUserInfoUseCase
import com.main.profile.presentation.communication.ProfileCommunication
import com.main.profile.presentation.communication.ProfileMotionToastCommunication
import com.main.profile.presentation.communication.ProfileUserInfoCommunication
import com.main.profile.presentation.viewmodel.ProfileViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ProfilePresentationModule {

    @Provides
    fun provideProfileViewModelFactory(
        getUserInfoUseCase: GetUserInfoUseCase,
        saveUserInfoUseCase: SaveUserInfoUseCase,
        dispatchers: DispatchersList,
        profileCommunication: ProfileCommunication,
        profileNavigation: ProfileNavigation,
        manageImageRepository: ManageImageRepository
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            getUserInfoUseCase = getUserInfoUseCase,
            saveUserInfoUseCase = saveUserInfoUseCase,
            dispatchers = dispatchers,
            profileCommunication = profileCommunication,
            profileNavigation = profileNavigation,
            manageImageRepository = manageImageRepository
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