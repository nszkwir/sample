package com.spitzer.ui.feature.dashboard

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spitzer.ui.R
import com.spitzer.ui.components.loadingButton.OutlinedLoadingButton
import com.spitzer.ui.feature.settings.SettingsViewModel
import com.spitzer.ui.graphics.AnimatedBackground
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.topbar.SampleTopAppBarConfiguration
import com.spitzer.ui.layout.scaffold.topbar.LargeTopAppBar

@Composable
fun DashboardScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    // Windows configuration
    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color.Transparent
    val useDarkIcons = isSystemInDarkTheme()
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }

    ScaffoldLayout(
        backgroundContent = {
            AnimatedBackground()
        },
        topBarContent = {
            LargeTopAppBar(
                configuration = SampleTopAppBarConfiguration(
                    title = "Welcome!",
                    backgroundColor = Color.Transparent,
                    elementsColor = Color.White,
                    navIconId = R.drawable.baseline_arrow_back_24,
                    navIconContentDescription = stringResource(id = R.string.back_CD),
                    onNavIconClicked = onBackClicked
                ),
            )
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = "",
                placeholder = {
                    Text("Search country")
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                onValueChange = {}
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {


            if (false) {
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedLoadingButton(
                    text = stringResource(id = R.string.restoreCountriesData),
                    contentDescription = stringResource(id = R.string.restoreCountriesData_CD),
                    state = uiState.value.restoreButtonState
                ) {
                    viewModel.updateCountries()
                }
            }
        }
    }
}