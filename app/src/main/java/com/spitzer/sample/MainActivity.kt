package com.spitzer.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spitzer.sample.ui.theme.SampleTheme
import com.spitzer.ui.navigation.CountriesScreenNavigation
import com.spitzer.ui.navigation.countriesScreenNavigation
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
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = CountriesScreenNavigation.route,
                        modifier = Modifier,
                    ) {
                        countriesScreenNavigation(this, navHostController)
                    }
                }
            }
        }
    }
}
