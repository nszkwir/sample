package com.spitzer.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.spitzer.ui.feature.countryDetails.CountryDetailsScreen

object CountryDetailsNavigation : NavRoute(
    route = "CountryDetails",
    arguments = listOf<NamedNavArgument>(
        navArgument("cca3") {
            type = NavType.StringType
        }
    )
)

fun NavHostController.navigateToCountryDetailsScreen(
    cca3: String,
    navOptions: NavOptions? = null
) = navigate("${CountryDetailsNavigation.route}/${cca3}", navOptions)

fun countryDetailsScreenNavigation(
    navGraphBuilder: NavGraphBuilder,
    navHostController: NavHostController
) {
    navGraphBuilder.composable(
        route = "${CountryDetailsNavigation.route}/{cca3}",
        arguments = CountryDetailsNavigation.arguments
    ) {
        val countryCca3Code = it.arguments?.getString("cca3")
        CountryDetailsScreen(
            cca3 = countryCca3Code,
            onTopAppBarIconClicked = {
                // not used
            },
            onTopAppBarNavIconClicked = {
                navHostController.navigateUp()
            }
        )
    }
}
