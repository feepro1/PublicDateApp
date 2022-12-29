package com.main.navigation

sealed class NavigationFlow {
    object LoginFlow : NavigationFlow()
    object RegisterFlow : NavigationFlow()
}