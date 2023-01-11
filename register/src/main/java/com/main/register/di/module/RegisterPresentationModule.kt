package com.main.register.di.module

import com.main.core.DispatchersList
import com.main.core.ManageImageRepository
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
        validateStartRegisterData: ValidateStartRegisterData,
        manageImageRepository: ManageImageRepository
    ): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            registerUseCase = registerUseCase,
            registerCommunication = registerCommunication,
            dispatchers = dispatchers,
            registerNavigation = registerNavigation,
            validateStartRegisterData = validateStartRegisterData,
            manageImageRepository = manageImageRepository
        )
    }

    @Provides
    fun provideLoginCommunication(
        registerEmailCommunication: RegisterEmailCommunication,
        registerPasswordCommunication: RegisterPasswordCommunication,
        registerConfirmPasswordCommunication: RegisterConfirmPasswordCommunication,
        registerFirstNameCommunication: RegisterFirstNameCommunication,
        registerLastNameCommunication: RegisterLastNameCommunication,
        registerRegisterDataCommunication: RegisterRegisterDataCommunication,
        registerMotionToastTextCommunication: RegisterMotionToastTextCommunication
    ): RegisterCommunication {
        return RegisterCommunication.Base(
            registerEmailCommunication = registerEmailCommunication,
            registerPasswordCommunication = registerPasswordCommunication,
            registerConfirmPasswordCommunication = registerConfirmPasswordCommunication,
            registerFirstNameCommunication = registerFirstNameCommunication,
            registerLastNameCommunication = registerLastNameCommunication,
            registerRegisterDataCommunication = registerRegisterDataCommunication,
            registerMotionToastTextCommunication = registerMotionToastTextCommunication
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
    fun provideRegisterMotionToastCommunication(): RegisterMotionToastTextCommunication{
        return RegisterMotionToastTextCommunication.Base()
    }

    @Provides
    fun provideDispatchers(): DispatchersList {
        return DispatchersList.Base()
    }

}