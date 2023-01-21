package com.main.chat.di.provider

import com.main.chat.di.component.ChatComponent

interface ProvideChatComponent {
    fun provideChatComponent(): ChatComponent
}