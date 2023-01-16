package com.main.profile.di.modules

import com.main.core.ManageImageRepository
import com.main.profile.data.database.ProfileFirebaseRepository
import com.main.profile.data.realization.GetUserInfoRepositoryImpl
import com.main.profile.data.realization.SaveUserInfoRepositoryImpl
import com.main.profile.domain.firebase.GetUserInfoRepository
import com.main.profile.domain.firebase.SaveUserInfoRepository
import dagger.Module
import dagger.Provides

@Module
class ProfileDataModule {

    @Provides
    fun provideGetUserInfoRepository(
        profileFirebaseRepository: ProfileFirebaseRepository
    ): GetUserInfoRepository {
        return GetUserInfoRepositoryImpl(profileFirebaseRepository)
    }

    @Provides
    fun provideSaveUserInfoRepository(
        profileFirebaseRepository: ProfileFirebaseRepository
    ): SaveUserInfoRepository {
        return SaveUserInfoRepositoryImpl(profileFirebaseRepository)
    }

    @Provides
    fun provideProfileFirebaseRepository(): ProfileFirebaseRepository {
        return ProfileFirebaseRepository.Base()
    }

    @Provides
    fun provideManageImageRepository(): ManageImageRepository {
        return ManageImageRepository.Base()
    }
}