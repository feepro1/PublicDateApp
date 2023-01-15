package com.main.dating.di.component

import com.main.dating.di.modules.DatingDataModule
import com.main.dating.di.modules.DatingDomainModule
import com.main.dating.di.modules.DatingPresentationModule
import com.main.dating.presentation.ui.DatingFragment
import dagger.Component

@Component(modules = [
    DatingPresentationModule::class,
    DatingDomainModule::class,
    DatingDataModule::class
])
interface DatingComponent {
    fun inject(datingFragment: DatingFragment)
}