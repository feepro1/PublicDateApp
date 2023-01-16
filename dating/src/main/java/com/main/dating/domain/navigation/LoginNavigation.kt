package com.main.dating.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.main.core.navigation.DeepLinks.DATING_DEEP_LINK
import com.main.core.navigation.DeepLinks.PROFILE_DEEP_LINK
import com.main.core.navigation.DeepLinks.START_REGISTER_DEEP_LINK
import com.main.dating.R

interface DatingNavigation {

    fun navigateToProfileFragment(navController: NavController)

    class Base: DatingNavigation {

        override fun navigateToProfileFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.datingNavGraph, true).build()
            navController.navigate(PROFILE_DEEP_LINK, navOptions)
        }
    }
}