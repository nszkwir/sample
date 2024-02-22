package com.spitzer.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

open class NavRoute(
    open val route: String,
    val arguments: List<NamedNavArgument> = listOf<NamedNavArgument>(),
    val deepLinks: List<NavDeepLink> = emptyList()
)
