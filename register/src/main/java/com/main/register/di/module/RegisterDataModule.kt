package com.main.register.di.module

import com.main.register.data.validation.ValidateRegisterData
import dagger.Module
import dagger.Provides

@Module
class RegisterDataModule {

    @Provides
    fun provideValidateRegisterData(): ValidateRegisterData {
        return ValidateRegisterData.Base()
    }

}