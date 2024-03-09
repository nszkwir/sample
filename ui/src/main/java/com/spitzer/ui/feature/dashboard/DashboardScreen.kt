package com.spitzer.ui.feature.dashboard

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spitzer.ui.R
import com.spitzer.ui.components.dashboardCard.DashboardCard
import com.spitzer.ui.components.dashboardCard.DashboardCountryCard
import com.spitzer.ui.components.transparentSearchField.TransparentSearchField
import com.spitzer.ui.graphics.AnimateSlideFromLeft
import com.spitzer.ui.graphics.AnimateSlideFromRight
import com.spitzer.ui.graphics.AnimatedBackground
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.topbar.LargeTopAppBar
import com.spitzer.ui.layout.scaffold.topbar.SampleTopAppBarConfiguration

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onTopAppBarNavIconClicked: () -> Unit,
    onTopAppBarIconClicked: () -> Unit,
    onNavigateToFullCountryList: () -> Unit,
    onCountryClicked: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val showCountriesError = uiState.searchIsActive && uiState.searchingCountriesError
    val showCountriesLoading = uiState.searchIsActive && uiState.searchingCountriesProgress
    val showCountries = uiState.searchIsActive && !showCountriesError && !showCountriesLoading
    // We only show the dashboard cards when there are no countries to show
    val showDashboardCards = !uiState.searchIsActive

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
                    title = stringResource(id = R.string.welcome),
                    backgroundColor = Color.Transparent,
                    elementsColor = Color.White,
                    buttonIconId = R.drawable.baseline_settings_24,
                    buttonContentDescription = stringResource(id = R.string.settings),
                    onButtonClicked = onTopAppBarIconClicked
                ),
            )
        },
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp, start = 10.dp)
            ) {
                TransparentSearchField(
                    modifier = Modifier.fillMaxWidth(),
                    searchText = uiState.searchText ?: "",
                    placeholder = stringResource(id = R.string.searchCountries),
                    onSearchTextChange = viewModel::onSearchTextChange,
                    onClearSearchText = viewModel::clearSearchText
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding(),
            ) {
                item(key = "dashboardCards") {
                    AnimateSlideFromLeft(isVisible = showDashboardCards) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            DashboardCard(
                                modifier = Modifier.padding(end = 40.dp),
                                title = stringResource(id = R.string.flagsAndCoatOfArms),
                                subtitle = stringResource(id = R.string.exploreTheFlagsAndCoatOfArms),
                                imageId = R.drawable.baseline_outlined_flag_24,
                                onClickActionDescription = stringResource(id = R.string.exploreTheFlagsAndCoatOfArms_CD),
                                onCardClicked = onNavigateToFullCountryList
                            )
                        }
                    }

                    AnimateSlideFromRight(isVisible = showDashboardCards) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            DashboardCard(
                                modifier = Modifier.padding(start = 40.dp),
                                title = stringResource(id = R.string.fullInformation),
                                subtitle = stringResource(id = R.string.learnMoreAboutCountries),
                                imageId = R.drawable.baseline_search_24,
                                onClickActionDescription = stringResource(id = R.string.exploreTheFlagsAndCoatOfArms_CD),
                                leftIcon = false,
                                onCardClicked = {} // TODO
                            )
                        }
                    }

                    AnimateSlideFromLeft(isVisible = showDashboardCards) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            DashboardCard(
                                modifier = Modifier.padding(end = 40.dp),
                                title = stringResource(id = R.string.statistics),
                                subtitle = stringResource(id = R.string.compareAndGetInsight),
                                imageId = R.drawable.baseline_query_stats_24,
                                onClickActionDescription = stringResource(id = R.string.compareAndGetInsight_CD),
                                onCardClicked = {} // TODO
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))
                }

                if (showCountriesError) {
                    item(key = "errorDashboardCard") {
                        DashboardCard(
                            modifier = Modifier.padding(end = 40.dp),
                            title = stringResource(id = R.string.ooops),
                            subtitle = stringResource(id = R.string.errorSearchingCountries),
                            imageId = R.drawable.baseline_sync_problem_24,
                            isCompactMode = false,
                        )
                    }
                }

                if (showCountriesLoading) {
                    item(key = "countriesLoaderIndicator") {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )
                    }
                }

                if (showCountries) {
                    with(uiState.countries) {
                        if (this.isNullOrEmpty()) {
                            item(key = "emptyDashboardCard") {
                                DashboardCard(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    title = stringResource(id = R.string.sorry),
                                    subtitle = stringResource(id = R.string.weHaventFoundAnyCountry),
                                    imageId = R.drawable.baseline_search_off_24,
                                    isCompactMode = false
                                )
                            }
                        } else {
                            items(this, key = { it.cca3 }) { country ->
                                DashboardCountryCard(
                                    country = country,
                                    onClick = {
                                        onCountryClicked(it.cca3)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
