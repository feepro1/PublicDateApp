package com.example.profile.di.modules

import com.example.profile.domain.firebase.GetUserInfoRepository
import com.example.profile.domain.firebase.SaveUserInfoRepository
import com.example.profile.domain.usecases.GetUserInfoUseCase
import com.example.profile.domain.usecases.SaveUserInfoUseCase
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

}