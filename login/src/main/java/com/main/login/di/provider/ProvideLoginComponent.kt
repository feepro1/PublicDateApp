package com.main.login.di.provider

import com.main.login.di.component.LoginComponent

interface ProvideLoginComponent {

    fun provideLoginComponent(): LoginComponent
}