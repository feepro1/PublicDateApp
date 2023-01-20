package com.main.profile.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.profile.R
import com.main.core.navigation.DeepLinks.CHATS_DEEP_LINK
import com.main.core.navigation.DeepLinks.DATING_DEEP_LINK

interface ProfileNavigation {

    fun navigateToDatingFragment(navController: NavController)

    fun navigateToChatsFragment(navController: NavController)

    class Base: ProfileNavigation {

        override fun navigateToDatingFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.profileNavGraph, true).build()
            navController.navigate(DATING_DEEP_LINK, navOptions)
        }

        override fun navigateToChatsFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.profileNavGraph, true).build()
            navController.navigate(CHATS_DEEP_LINK, navOptions)
        }
    }
}