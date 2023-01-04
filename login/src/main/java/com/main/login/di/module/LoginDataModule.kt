package com.main.login.di.module

import com.main.login.data.validation.ValidateLoginData
import dagger.Module
import dagger.Provides

@Module
class LoginDataModule {

    @Provides
    fun provideValidateLoginData(): ValidateLoginData {
        return ValidateLoginData.Base()
    }
}