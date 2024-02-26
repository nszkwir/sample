package com.spitzer.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

object CountriesScreenNavigation : NavRoute(
    route = "CountriesScreen"
)

fun NavHostController.navigateToCountriesScreen(navOptions: NavOptions? = null) =
    navigate(CountriesScreenNavigation.route, navOptions)
