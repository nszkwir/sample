package com.spitzer.ui.feature.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spitzer.ui.R
import com.spitzer.ui.animations.AnimateSlideFromLeft
import com.spitzer.ui.animations.AnimateSlideFromRight
import com.spitzer.ui.components.dashboardCard.DashboardCard
import com.spitzer.ui.graphics.AnimatedBackground
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.topbar.LargeTopAppBar
import com.spitzer.ui.layout.scaffold.topbar.SampleTopAppBarConfiguration

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onCountryClicked: (String) -> Unit,
    onBackClicked: () -> Unit
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
                    navIconId = R.drawable.baseline_arrow_back_24,
                    navIconContentDescription = stringResource(id = R.string.back_CD),
                    onNavIconClicked = onBackClicked
                ),
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(top = 0.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.searchText ?: "",
                    placeholder = {
                        Text(stringResource(id = R.string.searchCountries))
                    },
                    trailingIcon = {
                        if (uiState.searchText.isNullOrEmpty()) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_search_24),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                modifier = Modifier.clickable { viewModel.clearSearchText() },
                                painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = null
                            )
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        errorContainerColor = Color.White,
                        disabledContainerColor = Color.White
                    ),
                    onValueChange = viewModel::onSearchTextChange
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
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
                                imageId = R.drawable.baseline_outlined_flag_24
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
                                leftIcon = false
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
                                imageId = R.drawable.baseline_query_stats_24
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))
                }

                if (showCountriesError) {
                    item {
                        DashboardCard(
                            modifier = Modifier.padding(end = 40.dp),
                            title = stringResource(id = R.string.ooops),
                            subtitle = stringResource(id = R.string.errorSearchingCountries),
                            imageId = R.drawable.baseline_sync_problem_24,
                            isCompactMode = false
                        )
                    }
                }

                if (showCountriesLoading) {
                    item {
                        CircularProgressIndicator()
                    }
                }

                if (showCountries) {
                    with(uiState.countries) {
                        if (this.isNullOrEmpty()) {
                            item {
                                DashboardCard(
                                    modifier = Modifier.padding(end = 40.dp),
                                    title = stringResource(id = R.string.sorry),
                                    subtitle = stringResource(id = R.string.weHaventFoundAnyCountry),
                                    imageId = R.drawable.baseline_search_off_24,
                                    isCompactMode = false
                                )
                            }
                        } else {
                            item {
                                Spacer(Modifier.size(80.dp))
                            }
                            items(this) { country ->
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Image(
                                        modifier = Modifier.size(
                                            width = 30.dp,
                                            height = 20.dp
                                        ),
                                        painter = rememberAsyncImagePainter(
                                            country.flagUrl
                                                ?: R.drawable.baseline_broken_image_24
                                        ), contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.size(20.dp))
                                    Text(
                                        text = country.name,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
