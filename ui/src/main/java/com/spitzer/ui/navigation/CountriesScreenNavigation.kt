package com.spitzer.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spitzer.ui.feature.countries.CountriesScreen

object CountriesScreenNavigation : NavRoute(
    route = "CountriesScreen"
)

fun NavHostController.navigateToCountriesScreen(navOptions: NavOptions? = null) =
    navigate(CountriesScreenNavigation.route, navOptions)

fun countriesScreenNavigation(
    navGraphBuilder: NavGraphBuilder,
    navHostController: NavHostController
) {
    navGraphBuilder.composable(
        route = CountriesScreenNavigation.route, arguments = CountriesScreenNavigation.arguments
    ) {
        CountriesScreen(
            fabButtonClicked = {
                // TODO navHostController::navigateToAddCountry,
            },
            onCountryClicked = {
                // TODO  navHostController.navigateToCountryDetails()
            },
            onTopAppBarIconClicked = {
                // not used
            },
            onTopAppBarNavIconClicked = {
                navHostController.navigateUp()
            }
        )
    }
}
