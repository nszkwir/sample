package com.spitzer.ui.feature.countryDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.ui.R
import com.spitzer.ui.components.badges.CountryBadge
import com.spitzer.ui.components.badges.getBadgeConfiguration
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.topbar.TopBarConfiguration
import com.spitzer.ui.layout.scaffold.topbar.TopBarLayout
import com.spitzer.ui.theme.BlueGray50

@Composable
fun CountryDetailsScreen(
    cca3: String?,
    viewModel: CountryDetailsViewModel = hiltViewModel(),
    onTopAppBarNavIconClicked: () -> Unit,
    onTopAppBarIconClicked: () -> Unit,
) {
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

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(cca3) {
        viewModel.getCountryData(cca3)
    }

    ScaffoldLayout(
        topBarContent = {
            TopBarLayout(
                configuration = TopBarConfiguration(
                    title = "",
                    navIconId = R.drawable.baseline_arrow_back_24,
                    navIconContentDescription = stringResource(id = R.string.back_CD),
                    onNavIconClicked = onTopAppBarNavIconClicked,
                    buttonIconId = R.drawable.baseline_edit_24,
                    buttonContentDescription = stringResource(
                        id = R.string.editCountry_CD,
                        uiState.country?.commonName ?: ""// TODO improve buttons configuration
                    ),
                    onButtonClicked = onTopAppBarIconClicked
                )
            )
        },
        isLoading = uiState.isLoading
    ) {
        with(uiState.country) {
            if (this != null)
                CountryDetailsLayout(country = this)
        }
    }
}


@Composable
fun CountryDetailsLayout(
    modifier: Modifier = Modifier,
    country: CountryDetailsUiModel,
    flagPainter: Painter = rememberAsyncImagePainter(
        country.flagUrl ?: R.drawable.baseline_broken_image_no_padding
    ),
    coatOfArmsPainter: Painter = rememberAsyncImagePainter(
        country.coatOfArmsUrl ?: R.drawable.baseline_broken_image_no_padding
    ),
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .defaultMinSize(minHeight = 200.dp)
                //.height(200.dp)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)),
            painter = flagPainter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .graphicsLayer(translationY = with(LocalDensity.current) { 14.dp.toPx() } * -1)
                .background(Color.White, RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 35.dp)
            ) {
                Column(
                    modifier = Modifier.weight(3f)
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.headlineLarge) {
                        Text(modifier = Modifier.padding(start = 20.dp), text = country.commonName)
                    }
                    ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = country.officialName ?: ""
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        modifier = Modifier.size(90.dp),
                        painter = coatOfArmsPainter,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                    )
                }
            }
            LazyRow(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(country.badges.mapNotNull { it.getBadgeConfiguration() }) {
                    CountryBadge(configuration = it)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            HorizontalDivider(thickness = 6.dp, color = BlueGray50)
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())

            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 35.dp)
                ) {
                    Column {
                        ProvideTextStyle(value = MaterialTheme.typography.titleMedium) {
                            Text(
                                modifier = Modifier.padding(start = 20.dp),
                                text = "Information"
                            )
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = if ((country.capitalsNames?.size
                                                ?: 0) > 1
                                        ) "Capital cities" else "Capital city"
                                    )
                                }
                                ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = country.capitalsNames?.joinToString(",") ?: ""
                                    )
                                }

                                Spacer(modifier = Modifier.size(10.dp))

                                ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = "Population"
                                    )
                                }
                                ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = country.population.toString()
                                    )
                                }
                                Spacer(modifier = Modifier.size(10.dp))

                                ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = "Area (km sqr.)"
                                    )
                                }
                                ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = country.area.toString()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = "Official languages"
                                    )
                                }
                                ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = country.officialLanguages.joinToString(", ")
                                    )
                                }

                                Spacer(modifier = Modifier.size(10.dp))

                                ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = if (country.continents.size > 1) "Continents" else "Continent"
                                    )
                                }
                                ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = if (country.continents.isEmpty()) "Unknown"
                                        else country.continents.joinToString(", ")
                                    )
                                }
                                Spacer(modifier = Modifier.size(10.dp))

                                ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = "Subregion"
                                    )
                                }
                                ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                                    Text(
                                        modifier = Modifier.padding(start = 20.dp),
                                        text = country.subregion?.let { it } ?: "Unknown"
                                    )
                                }
                            }
                        }
                    }
                }
                // TODO maps: clickable to show the country on google maps
                // TODO show statistics like global position regarding population, area, gini
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Prev1() {
    CountryDetailsLayout(
        country = TestCountryModelProvider.getTestCountryModel()
            .mapToCountryDetailsModel(emptyMap()),
        flagPainter = painterResource(R.drawable.baseline_broken_image_no_padding),
        coatOfArmsPainter = painterResource(R.drawable.baseline_broken_image_no_padding),
    )
}
