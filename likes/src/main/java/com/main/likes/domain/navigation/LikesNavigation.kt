package com.main.likes.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.main.core.navigation.DeepLinks.CHAT_DEEP_LINK
import com.main.likes.R

interface LikesNavigation {

    fun navigateToDatingFragment(navController: NavController)

    fun navigateToChatFragment(navController: NavController)

    class Base : LikesNavigation {
        override fun navigateToDatingFragment(navController: NavController) {
            navController.popBackStack()
        }

        override fun navigateToChatFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.likesNavGraph, true).build()
            navController.navigate(CHAT_DEEP_LINK, navOptions)
        }
    }
}