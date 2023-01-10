package com.main.register.di.module

import com.main.core.dispatchers.DispatchersList
import com.main.register.data.validation.ValidateStartRegisterData
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.usecase.RegisterUseCase
import com.main.register.presentation.communication.*
import com.main.register.presentation.viewmodel.RegisterViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RegisterPresentationModule {

    @Provides
    fun provideRegisterViewModelFactory(
        registerUseCase: RegisterUseCase,
        registerCommunication: RegisterCommunication,
        dispatchers: DispatchersList,
        registerNavigation: RegisterNavigation,
        validateStartRegisterData: ValidateStartRegisterData
    ): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            registerUseCase = registerUseCase,
            registerCommunication = registerCommunication,
            dispatchers = dispatchers,
            registerNavigation = registerNavigation,
            validateStartRegisterData = validateStartRegisterData
        )
    }

    @Provides
    fun provideLoginCommunication(
        registerEmailCommunication: RegisterEmailCommunication,
        registerPasswordCommunication: RegisterPasswordCommunication,
        registerConfirmPasswordCommunication: RegisterConfirmPasswordCommunication,
        registerFirstNameCommunication: RegisterFirstNameCommunication,
        registerLastNameCommunication: RegisterLastNameCommunication,
        registerRegisterDataCommunication: RegisterRegisterDataCommunication
    ): RegisterCommunication {
        return RegisterCommunication.Base(
            registerEmailCommunication = registerEmailCommunication,
            registerPasswordCommunication = registerPasswordCommunication,
            registerConfirmPasswordCommunication = registerConfirmPasswordCommunication,
            registerFirstNameCommunication = registerFirstNameCommunication,
            registerLastNameCommunication = registerLastNameCommunication,
            registerRegisterDataCommunication
        )
    }

    @Provides
    fun provideRegisterEmailCommunication(): RegisterEmailCommunication {
        return RegisterEmailCommunication.Base()
    }

    @Provides
    fun provideRegisterPasswordCommunication(): RegisterPasswordCommunication {
        return RegisterPasswordCommunication.Base()
    }

    @Provides
    fun provideRegisterConfirmPasswordCommunication(): RegisterConfirmPasswordCommunication {
        return RegisterConfirmPasswordCommunication.Base()
    }

    @Provides
    fun provideRegisterFirstNameCommunication(): RegisterFirstNameCommunication {
        return RegisterFirstNameCommunication.Base()
    }

    @Provides
    fun provideRegisterLastNameCommunication(): RegisterLastNameCommunication {
        return RegisterLastNameCommunication.Base()
    }

    @Provides
    fun provideRegisterRegisterDataCommunication(): RegisterRegisterDataCommunication {
        return RegisterRegisterDataCommunication.Base()
    }

    @Provides
    fun provideDispatchers(): DispatchersList {
        return DispatchersList.Base()
    }

}