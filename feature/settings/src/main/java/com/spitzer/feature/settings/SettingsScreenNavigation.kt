package com.spitzer.feature.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.spitzer.ui.navigation.SettingsScreenNavigation

fun settingsScreenNavigation(
    navGraphBuilder: NavGraphBuilder,
    navHostController: NavHostController
) {
    navGraphBuilder.composable(
        route = SettingsScreenNavigation.route,
        arguments = SettingsScreenNavigation.arguments
    ) {
        SettingsScreen(
            onBackClicked = {
                navHostController.navigateUp()
            }
        )
    }
}
