package com.spitzer.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spitzer.ui.navigation.CountriesScreenNavigation
import com.spitzer.ui.navigation.countriesScreenNavigation
import com.spitzer.ui.navigation.settingsScreenNavigation
import com.spitzer.ui.theme.SampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            SampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = CountriesScreenNavigation.route,
                        modifier = Modifier,
                    ) {
                        countriesScreenNavigation(this, navHostController)
                        settingsScreenNavigation(this, navHostController)
                    }
                }
            }
        }
    }
}
