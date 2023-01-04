package com.main.login.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.main.core.navigation.DeepLinks.REGISTER_DEEP_LINK
import com.main.login.R

interface LoginNavigation {

    fun navigateToRegisterFragment(navController: NavController)

    class Base: LoginNavigation {

        override fun navigateToRegisterFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.loginNavGraph, true).build()
            navController.navigate(REGISTER_DEEP_LINK, navOptions)
        }
    }
}