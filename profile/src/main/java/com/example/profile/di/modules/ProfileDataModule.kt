package com.example.profile.di.modules

import com.example.profile.data.firebase.GetUserInfoRepositoryImpl
import com.example.profile.data.firebase.SaveUserInfoRepositoryImpl
import com.example.profile.domain.firebase.GetUserInfoRepository
import com.example.profile.domain.firebase.SaveUserInfoRepository
import dagger.Module
import dagger.Provides

@Module
class ProfileDataModule {

    @Provides
    fun provideGetUserInfoRepository(): GetUserInfoRepository {
        return GetUserInfoRepositoryImpl()
    }

    @Provides
    fun provideSaveUserInfoRepository(): SaveUserInfoRepository {
        return SaveUserInfoRepositoryImpl()
    }

}