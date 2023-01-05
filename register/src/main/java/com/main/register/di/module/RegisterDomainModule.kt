package com.main.register.di.module

import com.google.firebase.auth.FirebaseAuth
import com.main.register.data.realization.RegisterRepositoryImpl
import com.main.register.data.validation.ValidateRegisterData
import com.main.register.domain.exception.HandleFirebaseRegisterException
import com.main.register.domain.firebase.RegisterFirebaseRepository
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
    fun provideRegisterRepository(
        registerFirebaseRepository: RegisterFirebaseRepository,
        validateRegisterData: ValidateRegisterData
    ): RegisterRepository {
        return RegisterRepositoryImpl(
            registerFirebaseRepository = registerFirebaseRepository,
            validateRegisterData = validateRegisterData
        )
    }

    @Provides
    fun provideRegisterFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        handleFirebaseRegisterException: HandleFirebaseRegisterException
    ): RegisterFirebaseRepository {
        return RegisterFirebaseRepository.Base(
            firebaseAuth = firebaseAuth,
            handleFirebaseRegisterException = handleFirebaseRegisterException
        )
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideHandleFirebaseRegisterException(): HandleFirebaseRegisterException {
        return HandleFirebaseRegisterException.Base()
    }

    @Provides
    fun provideRegisterNavigation(): RegisterNavigation {
        return RegisterNavigation.Base()
    }

}