package com.main.profile.di.modules

import com.main.profile.data.firebase.GetUserInfoRepositoryImpl
import com.main.profile.data.firebase.SaveUserInfoRepositoryImpl
import com.main.profile.domain.firebase.GetUserInfoRepository
import com.main.profile.domain.firebase.SaveUserInfoRepository
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