package com.main.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        is NavigationFlow.LoginFlow -> navController.navigate(MainNavGraphDirections.loginFlow())
        is NavigationFlow.RegisterFlow -> navController.navigate(MainNavGraphDirections.registerFlow())
    }
}