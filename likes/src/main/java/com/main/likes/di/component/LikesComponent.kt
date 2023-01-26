package com.main.likes.di.component

import com.main.likes.presentation.ui.LikesFragment
import dagger.Component

@Component(modules = [

])
interface LikesComponent {
    fun inject(likesFragment: LikesFragment)
}