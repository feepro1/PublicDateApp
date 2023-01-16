package com.example.profile.di.component

import com.example.profile.di.modules.ProfileDataModule
import com.example.profile.di.modules.ProfileDomainModule
import com.example.profile.di.modules.ProfilePresentationModule
import com.example.profile.presentation.ui.ProfileFragment
import dagger.Component

@Component(modules = [
    ProfilePresentationModule::class,
    ProfileDomainModule::class,
    ProfileDataModule::class
])
interface ProfileComponent {
    fun inject(profileFragment: ProfileFragment)
}