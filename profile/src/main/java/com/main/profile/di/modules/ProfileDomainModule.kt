package com.main.profile.di.modules

import com.main.profile.domain.firebase.GetUserInfoRepository
import com.main.profile.domain.firebase.SaveUserInfoRepository
import com.main.profile.domain.navigation.ProfileNavigation
import com.main.profile.domain.usecases.GetUserInfoUseCase
import com.main.profile.domain.usecases.SaveUserInfoUseCase
import dagger.Module
import dagger.Provides

@Module
class ProfileDomainModule {

    @Provides
    fun provideGetUserInfoUseCase(
        getUserInfoRepository: GetUserInfoRepository
    ): GetUserInfoUseCase {
        return GetUserInfoUseCase(getUserInfoRepository)
    }

    @Provides
    fun provideSaveUserInfoUseCase(
        saveUserInfoRepository: SaveUserInfoRepository
    ): SaveUserInfoUseCase {
        return SaveUserInfoUseCase(saveUserInfoRepository)
    }

    @Provides
    fun provideProfileNavigation(): ProfileNavigation {
        return ProfileNavigation.Base()
    }

}