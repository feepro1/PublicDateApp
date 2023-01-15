package com.main.swaplike.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.main.dating.di.component.DaggerDatingComponent
import com.main.dating.di.component.DatingComponent
import com.main.dating.di.modules.DatingDataModule
import com.main.dating.di.modules.DatingDomainModule
import com.main.dating.di.modules.DatingPresentationModule
import com.main.dating.di.provider.ProvideDatingComponent
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

class Application : Application(), ProvideLoginComponent, ProvideRegisterComponent, ProvideDatingComponent {

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

    private val datingComponent by lazy {
        DaggerDatingComponent
            .builder()
            .datingPresentationModule(DatingPresentationModule())
            .datingDomainModule(DatingDomainModule())
            .datingDataModule(DatingDataModule())
            .build()
    }

    override fun provideLoginComponent(): LoginComponent = loginComponent

    override fun provideRegisterComponent(): RegisterComponent = registerComponent

    override fun provideDatingComponent(): DatingComponent = datingComponent

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }
}