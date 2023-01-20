package com.main.chats.di.provider

import com.main.chats.di.component.ChatsComponent

interface ProvideChatsComponent {

    fun provideChatsComponent(): ChatsComponent
}