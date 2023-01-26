package com.main.likes.di.component

import com.main.likes.di.modules.LikesDataModule
import com.main.likes.di.modules.LikesDomainModule
import com.main.likes.di.modules.LikesPresentationModule
import com.main.likes.presentation.ui.LikesFragment
import dagger.Component

@Component(modules = [
    LikesPresentationModule::class,
    LikesDomainModule::class,
    LikesDataModule::class
])
interface LikesComponent {
    fun inject(likesFragment: LikesFragment)
}