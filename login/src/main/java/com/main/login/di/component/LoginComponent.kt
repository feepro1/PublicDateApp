package com.main.login.di.component

import com.main.login.di.module.LoginPresentationModule
import com.main.login.presentation.ui.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LoginPresentationModule::class])
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}