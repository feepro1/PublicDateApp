package com.main.swaplike.app

import android.app.Application
import com.main.profile.di.component.DaggerProfileComponent
import com.main.profile.di.component.ProfileComponent
import com.main.profile.di.modules.ProfileDataModule
import com.main.profile.di.modules.ProfileDomainModule
import com.main.profile.di.modules.ProfilePresentationModule
import com.main.profile.di.provider.ProvideProfileComponent
import com.google.firebase.FirebaseApp
import com.main.chats.di.component.ChatsComponent
import com.main.chats.di.component.DaggerChatsComponent
import com.main.chats.di.modules.ChatsDataModule
import com.main.chats.di.modules.ChatsDomainModule
import com.main.chats.di.modules.ChatsPresentationModule
import com.main.chats.di.provider.ProvideChatsComponent
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

class Application : Application(), ProvideLoginComponent, ProvideRegisterComponent,
    ProvideDatingComponent, ProvideProfileComponent, ProvideChatsComponent {

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

    private val profileComponent by lazy {
        DaggerProfileComponent
            .builder()
            .profilePresentationModule(ProfilePresentationModule())
            .profileDomainModule(ProfileDomainModule())
            .profileDataModule(ProfileDataModule())
            .build()
    }

    private val chatsComponent by lazy {
        DaggerChatsComponent
            .builder()
            .chatsPresentationModule(ChatsPresentationModule())
            .chatsDomainModule(ChatsDomainModule())
            .chatsDataModule(ChatsDataModule())
            .build()
    }

    override fun provideLoginComponent(): LoginComponent = loginComponent

    override fun provideRegisterComponent(): RegisterComponent = registerComponent

    override fun provideDatingComponent(): DatingComponent = datingComponent

    override fun provideProfileComponent(): ProfileComponent = profileComponent

    override fun provideChatsComponent(): ChatsComponent = chatsComponent

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }
}