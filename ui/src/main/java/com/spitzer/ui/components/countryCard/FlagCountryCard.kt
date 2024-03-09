package com.spitzer.ui.components.countryCard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.spitzer.model.data.CountryModel
import com.spitzer.ui.R
import com.spitzer.ui.tooling.TestCountryModelProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlagCountryCard(
    modifier: Modifier = Modifier,
    country: CountryModel,
    onClick: (CountryModel) -> Unit,
    onClickActionDescription: String = stringResource(
        id = R.string.details_CD, country.name.common
    ),
    onLongClick: (CountryModel) -> Unit,
    onLongClickActionDescription: String = stringResource(
        id = R.string.edit_details_CD, country.name.common
    ),
    painter: Painter = rememberAsyncImagePainter(
        country.flags.svg ?: R.drawable.baseline_broken_image_no_padding
    ),
    iconPainter: Painter = painterResource(id = R.drawable.baseline_chevron_right_24),
    contentDescription: String = country.name.common
) {
    OutlinedCard(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
                this.customActions = listOf(
                    CustomAccessibilityAction(onClickActionDescription) {
                        onClick(country)
                        true
                    },
                    CustomAccessibilityAction(onLongClickActionDescription) {
                        onLongClick(country)
                        true
                    }
                )
            }
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .combinedClickable(
                        onClick = {
                            onClick(country)
                        },
                        onLongClick = {
                            onLongClick(country)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(6f)
                        .clipToBounds(),
                    contentScale = ContentScale.FillBounds,
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .weight(7f)
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = country.name.common,
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                    Text(
                        text = country.capital.firstOrNull()
                            ?: stringResource(id = R.string.unknown),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
                Column(
                    modifier = Modifier
                        .width(width = 40.dp)
                        .fillMaxHeight()
                        .weight(2f)
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            painter = iconPainter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(8.dp)
                                .align(Alignment.CenterVertically),
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun prev1() {
    FlagCountryCard(
        country = TestCountryModelProvider.getTestCountryModel(),
        painter = painterResource(R.drawable.baseline_broken_image_no_padding),
        onClick = {},
        onLongClick = {}
    )
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun prev2() {
    FlagCountryCard(
        country = TestCountryModelProvider.getTestCountryModel(),
        painter = painterResource(R.drawable.baseline_broken_image_no_padding),
        onClick = {},
        onLongClick = {}
    )
}

@Preview(device = "spec:width=600px,height=1200px,dpi=440")
@Composable
private fun prev3() {
    FlagCountryCard(
        country = TestCountryModelProvider.getTestCountryModel(),
        painter = painterResource(R.drawable.baseline_broken_image_no_padding),
        onClick = {},
        onLongClick = {}
    )
}
