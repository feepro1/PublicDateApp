package com.main.profile.di.component

import com.main.profile.di.modules.ProfileDataModule
import com.main.profile.di.modules.ProfileDomainModule
import com.main.profile.di.modules.ProfilePresentationModule
import com.main.profile.presentation.ui.ProfileFragment
import dagger.Component

@Component(modules = [
    ProfilePresentationModule::class,
    ProfileDomainModule::class,
    ProfileDataModule::class
])
interface ProfileComponent {
    fun inject(profileFragment: ProfileFragment)
}