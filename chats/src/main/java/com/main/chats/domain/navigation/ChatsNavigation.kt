package com.main.chats.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.main.chats.R
import com.main.core.navigation.DeepLinks

interface ChatsNavigation {

    fun navigateToProfileFragment(navController: NavController)

    fun navigateToDatingFragment(navController: NavController)

    class Base: ChatsNavigation {

        override fun navigateToProfileFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.chatsNavGraph, true).build()
            navController.navigate(DeepLinks.PROFILE_DEEP_LINK, navOptions)
        }

        override fun navigateToDatingFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.chatsNavGraph, true).build()
            navController.navigate(DeepLinks.DATING_DEEP_LINK, navOptions)
        }
    }
}