package com.main.dating.di.modules

import com.main.dating.data.realization.firebase.database.DatabaseRepositoryImpl
import com.main.dating.data.realization.firebase.repository.ManageUserRepositoryImpl
import com.main.dating.domain.exception.DatingHandleException
import com.main.dating.domain.firebase.database.DatabaseRepository
import com.main.dating.domain.firebase.repository.ManageUserRepository
import dagger.Module
import dagger.Provides

@Module
class DatingDataModule {

    @Provides
    fun provideDatabaseRepository(datingHandleException: DatingHandleException): DatabaseRepository {
        return DatabaseRepositoryImpl(datingHandleException = datingHandleException)
    }

    @Provides
    fun provideManageUserRepository(): ManageUserRepository {
        return ManageUserRepositoryImpl()
    }
}