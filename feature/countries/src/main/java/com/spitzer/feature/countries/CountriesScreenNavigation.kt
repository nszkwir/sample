package com.spitzer.feature.countries

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.spitzer.ui.navigation.CountriesScreenNavigation
import com.spitzer.ui.navigation.navigateToSettingsScreen


fun countriesScreenNavigation(
    navGraphBuilder: NavGraphBuilder,
    navHostController: NavHostController
) {
    navGraphBuilder.composable(
        route = CountriesScreenNavigation.route,
        arguments = CountriesScreenNavigation.arguments
    ) {
        CountriesScreen(
            fabButtonClicked = {}, // TODO navHostController::navigateToAddCountry,
            onCountryClicked = {
                // TODO  navHostController.navigateToEditCountry()
            },
            onSettingsClicked = {
                navHostController.navigateToSettingsScreen()
            }
        )
    }
}
