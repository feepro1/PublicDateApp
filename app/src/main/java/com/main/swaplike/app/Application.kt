package com.main.swaplike.app

import android.app.Application
import com.main.chat.data.storage.local.ChatCacheRepositoryImpl
import com.main.chat.di.component.ChatComponent
import com.main.chat.di.component.DaggerChatComponent
import com.main.chat.di.modules.ChatDataModule
import com.main.chat.di.modules.ChatDomainModule
import com.main.chat.di.modules.ChatPresentationModule
import com.main.chat.di.provider.ProvideChatComponent
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
import com.main.likes.di.component.DaggerLikesComponent
import com.main.likes.di.component.LikesComponent
import com.main.likes.di.modules.LikesDataModule
import com.main.likes.di.modules.LikesDomainModule
import com.main.likes.di.modules.LikesPresentationModule
import com.main.likes.di.provider.ProvideLikesComponent
import com.main.login.di.component.DaggerLoginComponent
import com.main.login.di.component.LoginComponent
import com.main.login.di.module.LoginDataModule
import com.main.login.di.module.LoginDomainModule
import com.main.login.di.module.LoginPresentationModule
import com.main.login.di.provider.ProvideLoginComponent
import com.main.profile.di.component.DaggerProfileComponent
import com.main.profile.di.component.ProfileComponent
import com.main.profile.di.modules.ProfileDataModule
import com.main.profile.di.modules.ProfileDomainModule
import com.main.profile.di.modules.ProfilePresentationModule
import com.main.profile.di.provider.ProvideProfileComponent
import com.main.register.di.component.DaggerRegisterComponent
import com.main.register.di.component.RegisterComponent
import com.main.register.di.module.RegisterDataModule
import com.main.register.di.module.RegisterDomainModule
import com.main.register.di.module.RegisterPresentationModule
import com.main.register.di.provider.ProvideRegisterComponent
import com.main.swaplike.data.cloud.firebase.ManageMessagesImpl
import com.main.swaplike.data.cloud.firebase.ManageNotificationTokenImpl
import com.main.swaplike.data.cloud.local.DatingDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Application : Application(), ProvideLoginComponent, ProvideRegisterComponent,
    ProvideDatingComponent, ProvideProfileComponent, ProvideChatsComponent,
    ProvideChatComponent, ProvideLikesComponent {

    private val manageNotificationToken = ManageNotificationTokenImpl()
    private val manageMessages = ManageMessagesImpl()

    override fun onCreate() {
        super.onCreate()
        val chatCacheRepository = ChatCacheRepositoryImpl(DatingDatabase.getInstance(applicationContext).chatDao())
        CoroutineScope(Dispatchers.IO).launch {
            manageNotificationToken.updateToken()
            val messages = manageMessages.receiveMessagesFromFirebase()
            manageMessages.deleteMessagesOnFirebase()
            manageMessages.addMessagesToLocalDatabase(messages, chatCacheRepository)
        }
    }

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
            .chatsDataModule(ChatsDataModule(DatingDatabase.getInstance(applicationContext).chatsDao()))
            .build()
    }

    private val chatComponent by lazy {
        DaggerChatComponent
            .builder()
            .chatPresentationModule(ChatPresentationModule())
            .chatDomainModule(ChatDomainModule())
            .chatDataModule(ChatDataModule(DatingDatabase.getInstance(applicationContext).chatDao()))
            .build()
    }

    private val likesComponent by lazy {
        DaggerLikesComponent
            .builder()
            .likesPresentationModule(LikesPresentationModule())
            .likesDomainModule(LikesDomainModule())
            .likesDataModule(LikesDataModule())
            .build()
    }

    override fun provideLoginComponent(): LoginComponent = loginComponent

    override fun provideRegisterComponent(): RegisterComponent = registerComponent

    override fun provideDatingComponent(): DatingComponent = datingComponent

    override fun provideProfileComponent(): ProfileComponent = profileComponent

    override fun provideChatsComponent(): ChatsComponent = chatsComponent

    override fun provideChatComponent(): ChatComponent = chatComponent

    override fun provideLikesComponent(): LikesComponent = likesComponent
}