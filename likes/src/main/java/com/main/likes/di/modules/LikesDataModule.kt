package com.main.likes.di.modules

import com.main.likes.data.realization.LikesRepositoryImpl
import com.main.likes.domain.firebase.LikesRepository
import dagger.Module
import dagger.Provides

@Module
class LikesDataModule {

    @Provides
    fun provideLikesRepository(): LikesRepository {
        return LikesRepositoryImpl()
    }

}