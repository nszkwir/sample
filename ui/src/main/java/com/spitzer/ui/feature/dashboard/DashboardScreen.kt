package com.spitzer.ui.feature.dashboard

import androidx.annotation.DrawableRes
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spitzer.ui.R
import com.spitzer.ui.animations.AnimateSlideFromLeft
import com.spitzer.ui.animations.AnimateSlideFromRight
import com.spitzer.ui.graphics.AnimatedBackground
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.topbar.LargeTopAppBar
import com.spitzer.ui.layout.scaffold.topbar.SampleTopAppBarConfiguration
import com.spitzer.ui.theme.SampleTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onCountryClicked: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isCountrySearchActive by remember(uiState) {
        derivedStateOf { (uiState.searchText?.length ?: 0) > 1 }
    }

    val showCountriesError by remember(uiState) {
        derivedStateOf { isCountrySearchActive && uiState.countriesUiState is DashboardCountriesUiState.Error }
    }

    val showCountriesLoading by remember(uiState) {
        derivedStateOf { isCountrySearchActive && uiState.countriesUiState is DashboardCountriesUiState.Loading }
    }

    val showCountries by remember(uiState) {
        derivedStateOf { isCountrySearchActive && uiState.countriesUiState is DashboardCountriesUiState.Success }
    }

    // We only show the dashboard cards when there are no countries to show
    val showDashboardCards = !showCountries

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
                        Text("Search countries ...")
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
                                title = "Flags and Coat of arms",
                                subtitle = "Explore the diverse flags and coats of arms of the countries!",
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
                                title = "Full information",
                                subtitle = "Learn more details about countries!",
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
                                title = "Statistics",
                                subtitle = "Compare and get insight about countries characteristics.",
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
                            title = "Ooops!",
                            subtitle = "An error occurred while searching for countries. Please try again.",
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
                    with((uiState.countriesUiState as DashboardCountriesUiState.Success).countries) {
                        if (this.isNullOrEmpty()) {
                            item {
                                DashboardCard(
                                    modifier = Modifier.padding(end = 40.dp),
                                    title = "Sorry!",
                                    subtitle = "We haven't found any country with those parameters. Please try again.",
                                    imageId = R.drawable.baseline_search_off_24,
                                    isCompactMode = false
                                )
                            }
                        } else {
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


@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    imageUrl: String? = null,
    @DrawableRes imageId: Int = R.drawable.baseline_broken_image_24,
    painter: Painter = rememberAsyncImagePainter(imageUrl ?: imageId),
    contentDescription: String? = null,
    isCompactMode: Boolean = true,
    leftIcon: Boolean = true,
    onCardClicked: () -> Unit = {}
) {

    OutlinedCard(
        modifier = modifier
            .semantics(mergeDescendants = true) {}
            //.padding(20.dp)
            .clickable { onCardClicked() },
    ) {
        if (isCompactMode) {
            Row(
                modifier = Modifier
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leftIcon) {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .weight(2f),
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                }
                Column(
                    modifier = Modifier.weight(8f)
                ) {
                    Text(
                        modifier = Modifier.align(
                            if (leftIcon) Alignment.Start else Alignment.End
                        ),
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            textAlign = if (leftIcon) TextAlign.Start else TextAlign.End
                        ),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        modifier = Modifier.align(
                            if (leftIcon) Alignment.Start else Alignment.End
                        ),
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            textAlign = if (leftIcon) TextAlign.Start else TextAlign.End
                        ),
                        minLines = 2,
                        maxLines = 2
                    )
                }
                if (!leftIcon) {
                    Spacer(modifier = Modifier.size(20.dp))
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .weight(2f),
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = title, style = MaterialTheme.typography.titleLarge, maxLines = 1)
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    minLines = 2,
                    maxLines = 2
                )
            }
        }
    }
}

@Preview
@Composable
fun DashboardCardPreview() {
    SampleTheme {
        Column {
            DashboardCard(
                title = "Statistics",
                subtitle = "Compare and get insight about countries characteristics.",
                painter = painterResource(id = R.drawable.baseline_query_stats_24),
            )
            Spacer(modifier = Modifier.size(20.dp))
            DashboardCard(
                title = "Statistics",
                subtitle = "Compare and get insight about countries characteristics.",
                painter = painterResource(id = R.drawable.baseline_query_stats_24),
                leftIcon = false,
            )
            Spacer(modifier = Modifier.size(20.dp))
            DashboardCard(
                title = "Statistics",
                subtitle = "Compare and get insight about countries characteristics.",
                painter = painterResource(id = R.drawable.baseline_query_stats_24),
                isCompactMode = false,
            )
        }
    }
}

data class DashboardCardModel(
    val title: String,
    val subtitle: String,
    val imageUrl: String? = null,
    @DrawableRes val imageId: Int = R.drawable.baseline_broken_image_24,
    val painter: Painter? = null,
    val contentDescription: String? = null,
    val onCardClicked: () -> Unit = {}
) {
    @Composable
    fun Compose() {
        DashboardCard(
            title = this.title,
            subtitle = this.subtitle,
            imageUrl = this.imageUrl,
            imageId = this.imageId,
            painter = this.painter ?: rememberAsyncImagePainter(this.imageUrl ?: this.imageId),
            contentDescription = this.contentDescription,
        )
    }
}
