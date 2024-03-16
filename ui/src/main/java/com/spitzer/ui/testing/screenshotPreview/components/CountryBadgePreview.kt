package com.spitzer.ui.testing.screenshotPreview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spitzer.ui.R
import com.spitzer.ui.components.badges.CountryBadge
import com.spitzer.ui.components.badges.getBadgeConfiguration
import com.spitzer.ui.feature.countryDetails.CountryDetailsUiModel
import com.spitzer.ui.theme.SampleTheme

@Composable
fun CountryBadge_ScreenshotTestFunction() {
    val unMemberBadge = CountryDetailsUiModel.Badge.UnitedNations(true)
    val unNotMemberBadge = CountryDetailsUiModel.Badge.UnitedNations(false)
    val currencyBadge =
        CountryDetailsUiModel.Badge.Currency(code = "USD", symbol = "US$", name = "US dollar")
    val currencyBadge2 =
        CountryDetailsUiModel.Badge.Currency(code = "ARS", symbol = "AR$", name = "Peso Argentino currency extremely long name")
    val phoneBadge1 = CountryDetailsUiModel.Badge.Phone(code = "+34")
    val phoneBadge2 = CountryDetailsUiModel.Badge.Phone(code = "+55")
    val continentBadge1 =
        CountryDetailsUiModel.Badge.Continent(CountryDetailsUiModel.Badge.Continent.Continent.SOUTH_AMERICA)
    val continentBadge2 =
        CountryDetailsUiModel.Badge.Continent(CountryDetailsUiModel.Badge.Continent.Continent.ANTARCTICA)
    SampleTheme {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Row {
                unMemberBadge.getBadgeConfiguration()?.let {
                    CountryBadge(
                        configuration = it,
                        badgePainter = painterResource(R.drawable.baseline_check_24),
                        mainIconPainter = painterResource(R.drawable.united_nations)
                    )
                }
                Spacer(Modifier.size(20.dp))
                unNotMemberBadge.getBadgeConfiguration()?.let {
                    CountryBadge(
                        configuration = it,
                        badgePainter = painterResource(R.drawable.baseline_close_24),
                        mainIconPainter = painterResource(R.drawable.united_nations)
                    )
                }
            }
            Spacer(Modifier.size(20.dp))
            Row {
                currencyBadge.getBadgeConfiguration()?.let {
                    CountryBadge(configuration = it)
                }
                Spacer(Modifier.size(20.dp))
                currencyBadge2.getBadgeConfiguration()?.let {
                    CountryBadge(configuration = it)
                }
            }
            Spacer(Modifier.size(20.dp))
            Row {
                phoneBadge1.getBadgeConfiguration()?.let {
                    CountryBadge(
                        configuration = it,
                        mainIconPainter = painterResource(R.drawable.baseline_phone_24)
                    )
                }
                Spacer(Modifier.size(20.dp))
                phoneBadge2.getBadgeConfiguration()?.let {
                    CountryBadge(
                        configuration = it,
                        mainIconPainter = painterResource(R.drawable.baseline_phone_24)
                    )
                }
            }
            Spacer(Modifier.size(20.dp))
            Row {
                continentBadge1.getBadgeConfiguration()?.let {
                    CountryBadge(
                        configuration = it,
                        mainIconPainter = painterResource(R.drawable.south_america)
                    )
                }
                Spacer(Modifier.size(20.dp))
                continentBadge2.getBadgeConfiguration()?.let {
                    CountryBadge(
                        configuration = it,
                        mainIconPainter = painterResource(R.drawable.world)
                    )
                }
            }
        }
    }
}
