package com.main.login.di.component

import com.main.login.di.module.LoginDataModule
import com.main.login.di.module.LoginDomainModule
import com.main.login.di.module.LoginPresentationModule
import com.main.login.presentation.ui.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    LoginPresentationModule::class,
    LoginDomainModule::class,
    LoginDataModule::class
])
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}