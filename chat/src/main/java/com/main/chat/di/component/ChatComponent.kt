package com.main.chat.di.component

import com.main.chat.di.modules.ChatDataModule
import com.main.chat.di.modules.ChatDomainModule
import com.main.chat.di.modules.ChatPresentationModule
import com.main.chat.presentation.ui.ChatFragment
import dagger.Component

@Component(modules = [
    ChatPresentationModule::class,
    ChatDomainModule::class,
    ChatDataModule::class
])
interface ChatComponent {
    fun inject(chatFragment: ChatFragment)
}