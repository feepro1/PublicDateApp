package com.main.register.di.component

import com.main.register.di.module.RegisterDataModule
import com.main.register.di.module.RegisterDomainModule
import com.main.register.di.module.RegisterPresentationModule
import com.main.register.presentation.ui.finish.FinishRegisterFragment
import com.main.register.presentation.ui.start.StartRegisterFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RegisterPresentationModule::class,
    RegisterDomainModule::class,
    RegisterDataModule::class
])
interface RegisterComponent {
    fun inject(startRegisterFragment: StartRegisterFragment)
    fun inject(finishRegisterFragment: FinishRegisterFragment)
}