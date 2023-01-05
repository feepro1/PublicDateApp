package com.main.register.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.main.register.data.database.FirebaseUserStorageRepository
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
        firebase: Firebase,
        handleFirebaseRegisterException: HandleFirebaseRegisterException,
        firebaseUserStorageRepository: FirebaseUserStorageRepository
    ): RegisterFirebaseRepository {
        return RegisterFirebaseRepository.Base(
            firebase = firebase,
            handleFirebaseRegisterException = handleFirebaseRegisterException,
            firebaseUserStorageRepository = firebaseUserStorageRepository
        )
    }

    @Provides
    fun provideFirebaseAuth(): Firebase {
        return Firebase
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