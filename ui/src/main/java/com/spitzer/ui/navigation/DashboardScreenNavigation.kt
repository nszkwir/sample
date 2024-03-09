package com.spitzer.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spitzer.ui.feature.dashboard.DashboardScreen

object DashboardScreenNavigation : NavRoute(
    route = "DashboardScreen"
)

fun NavHostController.navigateToDashboardScreen(navOptions: NavOptions? = null) =
    navigate(DashboardScreenNavigation.route, navOptions)

fun dashboardScreenNavigation(
    navGraphBuilder: NavGraphBuilder,
    navHostController: NavHostController
) {
    navGraphBuilder.composable(
        route = DashboardScreenNavigation.route, arguments = DashboardScreenNavigation.arguments
    ) {
        DashboardScreen(
            onTopAppBarIconClicked = {
                navHostController.navigateToSettingsScreen()
            },
            onTopAppBarNavIconClicked = {
                navHostController.navigateUp()
            },
            onNavigateToFullCountryList = {
                navHostController.navigateToCountriesScreen()
            },
            onCountryClicked = {
                navHostController.navigateToCountryDetailsScreen(it)
            },
        )
    }
}
