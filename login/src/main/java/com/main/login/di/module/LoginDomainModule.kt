package com.main.login.di.module

import com.main.login.data.realization.LoginRepositoryImpl
import com.main.login.domain.repository.LoginRepository
import com.main.login.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides

@Module
class LoginDomainModule {

    @Provides
    fun provideLoginRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository = loginRepository)
    }
}