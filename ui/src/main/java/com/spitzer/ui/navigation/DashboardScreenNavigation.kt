package com.spitzer.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spitzer.ui.feature.settings.SettingsScreen

object SettingsScreenNavigation : NavRoute(
    route = "SettingsScreen"
)

fun NavHostController.navigateToSettingsScreen(navOptions: NavOptions? = null) =
    navigate(SettingsScreenNavigation.route, navOptions)

fun settingsScreenNavigation(
    navGraphBuilder: NavGraphBuilder,
    navHostController: NavHostController
) {
    navGraphBuilder.composable(
        route = SettingsScreenNavigation.route, arguments = SettingsScreenNavigation.arguments
    ) {
        SettingsScreen(
            onBackClicked = {
                navHostController.navigateUp()
            }
        )
    }
}
