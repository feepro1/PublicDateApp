package com.main.swaplike.app

import android.app.Application
import com.main.login.di.component.DaggerLoginComponent
import com.main.login.di.component.LoginComponent
import com.main.login.di.module.LoginPresentationModule
import com.main.login.di.provider.ProvideLoginComponent

class Application : Application(), ProvideLoginComponent {

    private val loginComponent by lazy {
        DaggerLoginComponent
            .builder()
            .loginPresentationModule(LoginPresentationModule())
            .build()
    }

    override fun provideLoginComponent(): LoginComponent {
        return loginComponent
    }
}