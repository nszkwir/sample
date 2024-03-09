package com.spitzer.ui.components.badges

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.spitzer.ui.R
import com.spitzer.ui.feature.countryDetails.CountryDetailsUiModel
import com.spitzer.ui.theme.Green500
import com.spitzer.ui.theme.Red500

@Composable
fun CountryBadge(
    modifier: Modifier = Modifier,
    configuration: CountryBadgeConfiguration,
    showBadge: Boolean = configuration.badgeContent != Content.NONE,
    badgePainter: Painter = rememberAsyncImagePainter(configuration.badgeIcon),
    badgeContentColor: Color = configuration.badgeContentColor
        ?: MaterialTheme.colorScheme.background,
    badgeContainerColor: Color = configuration.badgeContainerColor
        ?: MaterialTheme.colorScheme.primary,
    mainIconPainter: Painter = rememberAsyncImagePainter(configuration.mainIcon),
    mainContentColor: Color = configuration.mainContentColor ?: MaterialTheme.colorScheme.primary, // TODO setup background and content color
    mainBackgroundColor: Color = configuration.mainBackgroundColor
        ?: MaterialTheme.colorScheme.background,
    mainStrokeColor: Color = configuration.mainStrokeColor ?: Color.Gray,
    label: String = configuration.label
        ?: configuration.labelResourceId?.let { stringResource(id = it) } ?: "",
    contentDescription: String = configuration.contentDescription
        ?: configuration.contentDescriptionResourceId?.let { stringResource(id = it, label) } ?: "",
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .border(
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, mainStrokeColor)
            )
            .clip(RoundedCornerShape(3.dp))
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
            },
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Row(
                modifier = modifier
                    .padding(top = 5.dp)
                    .weight(4f)
                    .align(Alignment.CenterHorizontally)
            ) {
                when (configuration.mainContent) {
                    Content.ICON -> {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(45.dp),
                            painter = mainIconPainter,
                            contentDescription = null,
                        )
                    }

                    Content.TEXT -> {
                        ProvideTextStyle(
                            value = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.sp,
                                lineHeight = 14.sp,
                                fontWeight = FontWeight(700)
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(top = 5.dp),
                                text = configuration.mainText ?: "",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }

                    Content.NONE -> {}
                }
            }
            Row(
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterHorizontally)
            ) {
                ProvideTextStyle(
                    value = MaterialTheme.typography.labelSmall.copy(
                        textAlign = TextAlign.Center,
                        lineHeight = 10.sp,
                    )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = label,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        if (showBadge) {
            Badge(
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.TopEnd),
                contentColor = badgeContentColor,
                containerColor = badgeContainerColor,
            ) {
                if (configuration.badgeContent == Content.ICON) Icon(
                    modifier = Modifier.size(9.dp),
                    painter = badgePainter,
                    contentDescription = null,
                )
                else Text(configuration.badgeText ?: "")
            }
        }
    }
}

enum class Content {
    ICON, TEXT, NONE
}

data class CountryBadgeConfiguration(
    val badgeContent: Content = Content.ICON,
    val badgeContentColor: Color? = null,
    val badgeContainerColor: Color? = null,
    @DrawableRes val badgeIcon: Int = R.drawable.baseline_broken_image_no_padding,
    val badgeText: String? = null,
    val mainContent: Content = Content.ICON,
    val mainContentColor: Color? = null,
    val mainBackgroundColor: Color? = null,
    val mainStrokeColor: Color? = null,
    @DrawableRes val mainIcon: Int? = R.drawable.baseline_broken_image_no_padding,
    val mainText: String? = null,
    @StringRes val labelResourceId: Int? = null,
    val label: String? = null,
    @StringRes val contentDescriptionResourceId: Int? = null,
    val contentDescription: String? = null
)

fun CountryDetailsUiModel.Badge.getBadgeConfiguration(): CountryBadgeConfiguration? {
    return when (this) {
        is CountryDetailsUiModel.Badge.UnitedNations -> CountryBadgeConfiguration(
            badgeContent = Content.ICON,
            badgeContentColor = Color.White,
            badgeContainerColor = if (this@getBadgeConfiguration.isMember) Green500 else Red500,
            badgeIcon = if (this@getBadgeConfiguration.isMember) R.drawable.baseline_check_24 else R.drawable.baseline_close_24,
            mainContent = Content.ICON,
            mainIcon = R.drawable.united_nations,
            labelResourceId = R.string.unitedNations,
            contentDescriptionResourceId = R.string.unitedNations_CD,
        )

        is CountryDetailsUiModel.Badge.Currency -> CountryBadgeConfiguration(
            badgeContent = Content.TEXT,
            badgeText = this@getBadgeConfiguration.symbol,
            mainContent = Content.TEXT,
            mainText = this@getBadgeConfiguration.code,
            label = this@getBadgeConfiguration.name,
            contentDescriptionResourceId = R.string.currency_CD,
        )

        is CountryDetailsUiModel.Badge.Phone -> CountryBadgeConfiguration(
            badgeContent = Content.TEXT,
            badgeText = this@getBadgeConfiguration.code,
            mainContent = Content.ICON,
            mainIcon = R.drawable.baseline_phone_24,
            labelResourceId = R.string.phone,
            contentDescriptionResourceId = R.string.phone_CD
        )

        is CountryDetailsUiModel.Badge.Continent -> CountryBadgeConfiguration(
            badgeContent = Content.NONE,
            mainContent = Content.ICON,
            mainIcon = when (this@getBadgeConfiguration.continent) {
                CountryDetailsUiModel.Badge.Continent.Continent.AFRICA -> R.drawable.africa
                CountryDetailsUiModel.Badge.Continent.Continent.EUROPE -> R.drawable.europe
                CountryDetailsUiModel.Badge.Continent.Continent.NORTH_AMERICA -> R.drawable.north_america
                CountryDetailsUiModel.Badge.Continent.Continent.SOUTH_AMERICA -> R.drawable.south_america
                CountryDetailsUiModel.Badge.Continent.Continent.ASIA -> R.drawable.asia
                CountryDetailsUiModel.Badge.Continent.Continent.OCEANIA -> R.drawable.oceania
                CountryDetailsUiModel.Badge.Continent.Continent.ANTARCTICA -> R.drawable.world
                CountryDetailsUiModel.Badge.Continent.Continent.UNDETERMINED -> R.drawable.world
            },
            labelResourceId = when (this@getBadgeConfiguration.continent) {
                CountryDetailsUiModel.Badge.Continent.Continent.AFRICA -> R.string.africa
                CountryDetailsUiModel.Badge.Continent.Continent.EUROPE -> R.string.europe
                CountryDetailsUiModel.Badge.Continent.Continent.NORTH_AMERICA -> R.string.northAmerica
                CountryDetailsUiModel.Badge.Continent.Continent.SOUTH_AMERICA -> R.string.southAmerica
                CountryDetailsUiModel.Badge.Continent.Continent.ASIA -> R.string.asia
                CountryDetailsUiModel.Badge.Continent.Continent.OCEANIA -> R.string.oceania
                CountryDetailsUiModel.Badge.Continent.Continent.ANTARCTICA -> R.string.antarctica
                CountryDetailsUiModel.Badge.Continent.Continent.UNDETERMINED -> R.string.unknown
            },
            contentDescriptionResourceId = R.string.continent_CD
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Prev1() {
    val unMemberBadge = CountryDetailsUiModel.Badge.UnitedNations(true)
    val unNotMemberBadge = CountryDetailsUiModel.Badge.UnitedNations(false)
    val currencyBadge =
        CountryDetailsUiModel.Badge.Currency(code = "USD", symbol = "US$", name = "US dollar")
    val currencyBadge2 =
        CountryDetailsUiModel.Badge.Currency(code = "ARS", symbol = "AR$", name = "Peso Argentino")
    val phoneBadge1 = CountryDetailsUiModel.Badge.Phone(code = "+34")
    val phoneBadge2 = CountryDetailsUiModel.Badge.Phone(code = "+55")
    val continentBadge1 =
        CountryDetailsUiModel.Badge.Continent(CountryDetailsUiModel.Badge.Continent.Continent.SOUTH_AMERICA)
    val continentBadge2 =
        CountryDetailsUiModel.Badge.Continent(CountryDetailsUiModel.Badge.Continent.Continent.ANTARCTICA)

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
            unNotMemberBadge.getBadgeConfiguration()?.let {
                CountryBadge(
                    configuration = it,
                    badgePainter = painterResource(R.drawable.baseline_close_24),
                    mainIconPainter = painterResource(R.drawable.united_nations)
                )
            }
        }
        Row {
            currencyBadge.getBadgeConfiguration()?.let {
                CountryBadge(configuration = it)
            }
            currencyBadge2.getBadgeConfiguration()?.let {
                CountryBadge(configuration = it)
            }
        }

        Row {
            phoneBadge1.getBadgeConfiguration()?.let {
                CountryBadge(
                    configuration = it,
                    mainIconPainter = painterResource(R.drawable.baseline_phone_24)
                )
            }
            phoneBadge2.getBadgeConfiguration()?.let {
                CountryBadge(
                    configuration = it,
                    mainIconPainter = painterResource(R.drawable.baseline_phone_24)
                )
            }
        }

        Row {
            continentBadge1.getBadgeConfiguration()?.let {
                CountryBadge(
                    configuration = it,
                    mainIconPainter = painterResource(R.drawable.south_america)
                )
            }
            continentBadge2.getBadgeConfiguration()?.let {
                CountryBadge(
                    configuration = it,
                    mainIconPainter = painterResource(R.drawable.world)
                )
            }
        }
    }
}
