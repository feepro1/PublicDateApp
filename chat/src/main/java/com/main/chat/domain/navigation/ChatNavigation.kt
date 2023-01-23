package com.main.chat.domain.navigation

import androidx.navigation.NavController

interface ChatNavigation {

    fun navigateToChatsFragment(navController: NavController)

    class Base : ChatNavigation {

        override fun navigateToChatsFragment(navController: NavController) {
            navController.popBackStack()
        }
    }
}