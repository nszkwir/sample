package com.spitzer.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spitzer.feature.countries.countriesScreenNavigation
import com.spitzer.feature.settings.settingsScreenNavigation
import com.spitzer.sample.ui.theme.SampleTheme
import com.spitzer.ui.navigation.CountriesScreenNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            SampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = CountriesScreenNavigation.route,
                        modifier = Modifier,
                    ) {
                        countriesScreenNavigation(
                            this,
                            navHostController
                        )
                        settingsScreenNavigation(this, navHostController)
                    }
                }
            }
        }
    }
}
