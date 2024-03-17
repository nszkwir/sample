package com.spitzer.ui.feature.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spitzer.ui.R
import com.spitzer.ui.components.loadingButton.OutlinedLoadingButton
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.topappbar.TopAppBar
import com.spitzer.ui.layout.scaffold.topappbar.TopAppBarConfiguration

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onTopAppBarNavIconClicked: () -> Unit,
    onTopAppBarIconClicked: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Windows configuration
    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color.Transparent
    val useDarkIcons = !isSystemInDarkTheme()
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }

    SettingsScreen(
        uiState = uiState,
        onTopAppBarNavIconClicked = onTopAppBarNavIconClicked,
        onTopAppBarIconClicked = onTopAppBarIconClicked,
        updateCountries = viewModel::updateCountries
    )
}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onTopAppBarNavIconClicked: () -> Unit = {},
    onTopAppBarIconClicked: () -> Unit = {},
    updateCountries: () -> Unit = {}
) {
    ScaffoldLayout(
        topBarContent = {
            TopAppBar(
                configuration = TopAppBarConfiguration(
                    title = stringResource(id = R.string.settings),
                    navIconId = R.drawable.baseline_arrow_back_24,
                    navIconContentDescription = stringResource(id = R.string.back_CD),
                    onNavIconClicked = onTopAppBarNavIconClicked
                )
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedLoadingButton(
                text = stringResource(id = R.string.restoreCountriesData),
                contentDescription = stringResource(id = R.string.restoreCountriesData_CD),
                state = uiState.restoreButtonState
            ) {
                updateCountries()
            }
        }
    }
}
