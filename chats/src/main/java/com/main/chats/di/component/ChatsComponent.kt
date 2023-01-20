package com.main.chats.di.component

import com.main.chats.di.modules.ChatsDataModule
import com.main.chats.di.modules.ChatsDomainModule
import com.main.chats.di.modules.ChatsPresentationModule
import com.main.chats.presentation.ui.ChatsFragment
import dagger.Component

@Component(modules = [
    ChatsPresentationModule::class,
    ChatsDomainModule::class,
    ChatsDataModule::class
])
interface ChatsComponent {
    fun inject(chatsFragment: ChatsFragment)
}