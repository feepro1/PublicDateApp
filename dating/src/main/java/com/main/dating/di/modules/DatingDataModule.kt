package com.main.dating.di.modules

import com.main.dating.data.realization.firebase.database.DatabaseRepositoryImpl
import com.main.dating.data.realization.firebase.repository.ManageUserRepositoryImpl
import com.main.dating.domain.exception.DatingDatabaseHandleException
import com.main.dating.domain.firebase.database.DatabaseRepository
import com.main.dating.domain.firebase.repository.ManageUserRepository
import dagger.Module
import dagger.Provides

@Module
class DatingDataModule {

    @Provides
    fun provideDatabaseRepository(datingDatabaseHandleException: DatingDatabaseHandleException): DatabaseRepository {
        return DatabaseRepositoryImpl(datingDatabaseHandleException = datingDatabaseHandleException)
    }

    @Provides
    fun provideManageUserRepository(
        datingDatabaseHandleException: DatingDatabaseHandleException
    ): ManageUserRepository {
        return ManageUserRepositoryImpl(datingDatabaseHandleException)
    }
}