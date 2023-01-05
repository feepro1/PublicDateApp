package com.main.register.di.module

import com.main.register.data.realization.RegisterRepositoryImpl
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.repository.RegisterRepository
import com.main.register.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides

@Module
class RegisterDomainModule {

    @Provides
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCase(registerRepository = registerRepository)
    }

    @Provides
    fun provideRegisterRepository(): RegisterRepository {
        return RegisterRepositoryImpl()
    }

    @Provides
    fun provideRegisterNavigation(): RegisterNavigation {
        return RegisterNavigation.Base()
    }

}