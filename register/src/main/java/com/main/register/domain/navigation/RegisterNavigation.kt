package com.main.register.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.main.core.navigation.DeepLinks
import com.main.register.R

interface RegisterNavigation {

    fun navigateToLoginFragment(navController: NavController)

    class Base: RegisterNavigation {

        override fun navigateToLoginFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.registerNavGraph, true).build()
            navController.navigate(DeepLinks.LOGIN_DEEP_LINK, navOptions)
        }
    }
}