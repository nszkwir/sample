package com.spitzer.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

object SettingsScreenNavigation : NavRoute(
    route = "SettingsScreen"
)

fun NavHostController.navigateToSettingsScreen(navOptions: NavOptions? = null) =
    navigate(SettingsScreenNavigation.route, navOptions)
