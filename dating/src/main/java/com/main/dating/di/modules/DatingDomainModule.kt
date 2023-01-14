package com.main.dating.di.modules

import com.main.dating.domain.firebase.database.DatabaseRepository
import com.main.dating.domain.firebase.repository.ManageUserRepository
import com.main.dating.domain.interactor.DatingInteractor
import com.main.dating.domain.usecases.DislikeUserUseCase
import com.main.dating.domain.usecases.GetUsersFromDatabaseUseCase
import com.main.dating.domain.usecases.LikeUserUseCase
import dagger.Module
import dagger.Provides

@Module
class DatingDomainModule {

    @Provides
    fun provideDatingInteractor(
        getUsersFromDatabaseUseCase: GetUsersFromDatabaseUseCase,
        likeUserUseCase: LikeUserUseCase,
        dislikeUserUseCase: DislikeUserUseCase
    ): DatingInteractor {
        return DatingInteractor(
            getUsersFromDatabaseUseCase = getUsersFromDatabaseUseCase,
            likeUserUseCase = likeUserUseCase,
            dislikeUserUseCase = dislikeUserUseCase
        )
    }

    @Provides
    fun provideGetUsersFromDatabaseUseCase(
        databaseRepository: DatabaseRepository
    ): GetUsersFromDatabaseUseCase {
        return GetUsersFromDatabaseUseCase(databaseRepository = databaseRepository)
    }

    @Provides
    fun provideLikeUserUseCase(
        manageUserRepository: ManageUserRepository
    ): LikeUserUseCase {
        return LikeUserUseCase(manageUserRepository = manageUserRepository)
    }

    @Provides
    fun provideDislikeUserUseCase(
        manageUserRepository: ManageUserRepository
    ): DislikeUserUseCase {
        return DislikeUserUseCase(manageUserRepository = manageUserRepository)
    }
}