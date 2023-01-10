package com.main.register.di.module

import com.google.firebase.ktx.Firebase
import com.main.register.data.database.FirebaseUserStorageRepository
import com.main.register.data.validation.ValidateFinishRegisterData
import com.main.register.data.validation.ValidateStartRegisterData
import dagger.Module
import dagger.Provides

@Module
class RegisterDataModule {

    @Provides
    fun provideValidateFinishRegisterData(): ValidateFinishRegisterData {
        return ValidateFinishRegisterData.Base()
    }

    @Provides
    fun provideValidateStartRegisterData(): ValidateStartRegisterData {
        return ValidateStartRegisterData.Base()
    }

    @Provides
    fun provideFirebaseUserStorageRepository(firebase: Firebase): FirebaseUserStorageRepository {
        return FirebaseUserStorageRepository.Base(firebase = firebase)
    }

}