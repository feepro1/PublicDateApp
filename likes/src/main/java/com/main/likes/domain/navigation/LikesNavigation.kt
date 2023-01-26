package com.main.likes.domain.navigation

import androidx.navigation.NavController

interface LikesNavigation {

    fun navigateToDatingFragment(navController: NavController)

    class Base : LikesNavigation {
        override fun navigateToDatingFragment(navController: NavController) {
            navController.popBackStack()
        }
    }
}