package com.spitzer.ui.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spitzer.ui.R
import com.spitzer.ui.components.loadingButton.OutlinedLoadingButton
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.topbar.TopBarConfiguration
import com.spitzer.ui.layout.scaffold.topbar.TopBarLayout

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ScaffoldLayout(
        topBarContent = {
            TopBarLayout(
                configuration = TopBarConfiguration(
                    title = stringResource(id = R.string.settings),
                    navIconId = R.drawable.baseline_arrow_back_24,
                    navIconContentDescription = stringResource(id = R.string.back_CD),
                    onNavIconClicked = onBackClicked
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
                state = uiState.value.restoreButtonState
            ) {
                viewModel.updateCountries()
            }
        }
    }
}
