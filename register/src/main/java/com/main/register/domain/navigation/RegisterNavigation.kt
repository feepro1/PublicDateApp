package com.main.register.domain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.main.core.navigation.DeepLinks
import com.main.core.navigation.DeepLinks.DATING_DEEP_LINK
import com.main.core.navigation.DeepLinks.FINISH_REGISTER_DEEP_LINK
import com.main.core.navigation.DeepLinks.LOGIN_DEEP_LINK
import com.main.register.R

interface RegisterNavigation {

    fun navigateToLoginFragment(navController: NavController)

    fun navigateToFinishRegisterFragment(navController: NavController)

    fun navigateToDatingFragment(navController: NavController)

    class Base: RegisterNavigation {

        override fun navigateToLoginFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.registerNavGraph, true).build()
            navController.navigate(LOGIN_DEEP_LINK, navOptions)
        }

        override fun navigateToFinishRegisterFragment(navController: NavController) {
            navController.navigate(FINISH_REGISTER_DEEP_LINK)
        }

        override fun navigateToDatingFragment(navController: NavController) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.registerNavGraph, true).build()
            navController.navigate(DATING_DEEP_LINK, navOptions)
        }
    }
}