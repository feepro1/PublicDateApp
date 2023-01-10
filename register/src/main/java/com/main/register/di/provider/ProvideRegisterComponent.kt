package com.main.register.di.provider

import com.main.register.di.component.RegisterComponent

interface ProvideRegisterComponent {

    fun provideRegisterComponent(): RegisterComponent
}