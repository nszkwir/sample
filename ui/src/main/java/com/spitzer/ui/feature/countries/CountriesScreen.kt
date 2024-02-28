package com.spitzer.ui.feature.countries

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spitzer.ui.R
import com.spitzer.ui.layout.CountryListLayout
import com.spitzer.ui.layout.scaffold.FABConfiguration
import com.spitzer.ui.layout.scaffold.FABLayout
import com.spitzer.ui.layout.scaffold.LoadingLayout
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.TopBarConfiguration
import com.spitzer.ui.layout.scaffold.TopBarLayout

@Composable
fun CountriesScreen(
    viewModel: CountriesViewModel = hiltViewModel(),
    fabButtonClicked: () -> Unit,
    onCountryClicked: () -> Unit,
    onSettingsClicked: () -> Unit
) {
    val uiState by viewModel.countriesState.collectAsStateWithLifecycle()

    val countries = when (uiState) {
        is CountriesUiState.Success -> (uiState as CountriesUiState.Success).countries
        else -> emptyList()
    }

    ScaffoldLayout(
        topBarContent = {
            TopBarLayout(
                configuration = TopBarConfiguration(
                    title = stringResource(id = R.string.countries),
                    buttonIconId = R.drawable.baseline_settings_24,
                    onButtonClicked = onSettingsClicked
                )
            )
        },
        fabContent = {
            FABLayout(
                configuration = FABConfiguration(
                    fabIconId = R.drawable.baseline_add_24,
                    onFABClicked = fabButtonClicked,
                    fabContentDescription = stringResource(id = R.string.addCountry_CD)
                )
            )
        },
        loadingContent = {
            LoadingLayout(Modifier.padding(top = it))
        }
    ) {
        CountryListLayout(
            countries = countries,
            onCountryClicked = {
                onCountryClicked.invoke()
            },
            refreshCountryList = viewModel::refreshCountyList
        )
    }
}
