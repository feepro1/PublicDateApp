package com.main.register.di.module

import com.google.firebase.ktx.Firebase
import com.main.register.data.database.FirebaseUserStorageRepository
import com.main.register.data.validation.ValidateRegisterData
import dagger.Module
import dagger.Provides

@Module
class RegisterDataModule {

    @Provides
    fun provideValidateRegisterData(): ValidateRegisterData {
        return ValidateRegisterData.Base()
    }

    @Provides
    fun provideFirebaseUserStorageRepository(firebase: Firebase): FirebaseUserStorageRepository {
        return FirebaseUserStorageRepository.Base(firebase = firebase)
    }

}