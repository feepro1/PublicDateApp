package com.main.swaplike.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.main.login.di.component.DaggerLoginComponent
import com.main.login.di.component.LoginComponent
import com.main.login.di.module.LoginDataModule
import com.main.login.di.module.LoginDomainModule
import com.main.login.di.module.LoginPresentationModule
import com.main.login.di.provider.ProvideLoginComponent
import com.main.register.di.component.DaggerRegisterComponent
import com.main.register.di.component.RegisterComponent
import com.main.register.di.module.RegisterDataModule
import com.main.register.di.module.RegisterDomainModule
import com.main.register.di.module.RegisterPresentationModule
import com.main.register.di.provider.ProvideRegisterComponent

class Application : Application(), ProvideLoginComponent, ProvideRegisterComponent {

    private val loginComponent by lazy {
        DaggerLoginComponent
            .builder()
            .loginPresentationModule(LoginPresentationModule())
            .loginDomainModule(LoginDomainModule())
            .loginDataModule(LoginDataModule())
            .build()
    }

    private val registerComponent by lazy {
        DaggerRegisterComponent
            .builder()
            .registerPresentationModule(RegisterPresentationModule())
            .registerDomainModule(RegisterDomainModule())
            .registerDataModule(RegisterDataModule())
            .build()
    }

    override fun provideLoginComponent(): LoginComponent {
        return loginComponent
    }

    override fun provideRegisterComponent(): RegisterComponent {
        return registerComponent
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }
}