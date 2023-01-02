package com.main.login.di.module

import com.main.core.dispatchers.DispatchersList
import com.main.login.domain.usecase.LoginUseCase
import com.main.login.presentation.communication.LoginCommunication
import com.main.login.presentation.communication.LoginEmailCommunication
import com.main.login.presentation.communication.LoginPasswordCommunication
import com.main.login.presentation.communication.LoginUsernameCommunication
import com.main.login.presentation.viewmodel.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginPresentationModule {

    @Provides
    fun provideDispatchers(): DispatchersList {
        return DispatchersList.Base()
    }

    @Provides
    fun provideLoginEmailCommunication(): LoginEmailCommunication {
        return LoginEmailCommunication.Base()
    }

    @Provides
    fun provideLoginPasswordCommunication(): LoginPasswordCommunication {
        return LoginPasswordCommunication.Base()
    }

    @Provides
    fun provideLoginUsernameCommunication(): LoginUsernameCommunication {
        return LoginUsernameCommunication.Base()
    }

    @Provides
    fun provideLoginCommunication(
        loginEmailCommunication: LoginEmailCommunication,
        loginUsernameCommunication: LoginUsernameCommunication,
        loginPasswordCommunication: LoginPasswordCommunication
    ): LoginCommunication {
        return LoginCommunication.Base(
            loginEmailCommunication = loginEmailCommunication,
            loginUsernameCommunication = loginUsernameCommunication,
            loginPasswordCommunication = loginPasswordCommunication
        )
    }
    @Provides
    fun provideLoginViewModelFactory(
        loginUseCase: LoginUseCase,
        loginCommunication: LoginCommunication,
        dispatchers: DispatchersList
    ): LoginViewModelFactory {
        return LoginViewModelFactory(
            loginUseCase = loginUseCase,
            loginCommunication = loginCommunication,
            dispatchers = dispatchers
        )
    }
}