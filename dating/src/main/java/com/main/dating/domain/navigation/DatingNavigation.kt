package com.main.dating.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.main.core.navigation.DeepLinks.CHATS_DEEP_LINK
import com.main.core.navigation.DeepLinks.LIKES_DEEP_LINK
import com.main.core.navigation.DeepLinks.PROFILE_DEEP_LINK
import com.main.dating.R

interface DatingNavigation {

    fun navigateToProfileFragment(navController: NavController)

    fun navigateToChatsFragment(navController: NavController)

    fun navigateToLikesFragment(navController: NavController)

    class Base: DatingNavigation {

        override fun navigateToProfileFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.datingNavGraph, true).build()
            navController.navigate(PROFILE_DEEP_LINK, navOptions)
        }

        override fun navigateToChatsFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.datingNavGraph, true).build()
            navController.navigate(CHATS_DEEP_LINK, navOptions)
        }

        override fun navigateToLikesFragment(navController: NavController) {
            navController.navigate(LIKES_DEEP_LINK)
        }
    }
}