package com.main.login.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.main.core.navigation.DeepLinks.FINISH_REGISTER_DEEP_LINK
import com.main.core.navigation.DeepLinks.START_REGISTER_DEEP_LINK
import com.main.login.R

interface LoginNavigation {

    fun navigateToRegisterFragment(navController: NavController)

    class Base: LoginNavigation {

        override fun navigateToRegisterFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.loginNavGraph, true).build()
            navController.navigate(START_REGISTER_DEEP_LINK, navOptions)
        }
    }
}