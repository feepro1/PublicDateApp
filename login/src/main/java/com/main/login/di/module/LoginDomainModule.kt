package com.main.login.di.module

import com.google.firebase.auth.FirebaseAuth
import com.main.login.data.realization.LoginRepositoryImpl
import com.main.login.data.validation.ValidateLoginData
import com.main.login.domain.exception.HandleFirebaseLoginException
import com.main.login.domain.firebase.LoginFirebaseRepository
import com.main.login.domain.navigation.LoginNavigation
import com.main.login.domain.repository.LoginRepository
import com.main.login.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides

@Module
class LoginDomainModule {

    @Provides
    fun provideLoginRepository(
        loginFirebaseRepository: LoginFirebaseRepository,
        validateLoginData: ValidateLoginData
    ): LoginRepository {
        return LoginRepositoryImpl(
            loginFirebaseRepository = loginFirebaseRepository,
            validateLoginData = validateLoginData
        )
    }

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository = loginRepository)
    }

    @Provides
    fun provideLoginFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        handleFirebaseLoginException: HandleFirebaseLoginException
    ): LoginFirebaseRepository {
        return LoginFirebaseRepository.Base(
            firebaseAuth = firebaseAuth,
            handleFirebaseLoginException = handleFirebaseLoginException
        )
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideHandleFirebaseException(): HandleFirebaseLoginException {
        return HandleFirebaseLoginException.Base()
    }

    @Provides
    fun provideLoginNavigation(): LoginNavigation {
        return LoginNavigation.Base()
    }
}