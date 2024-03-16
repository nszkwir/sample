package com.spitzer.ui.components.dashboardCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.ui.R
import com.spitzer.ui.feature.dashboard.DashboardCountryModel
import com.spitzer.ui.feature.dashboard.asDashboardCountryModel

@Composable
fun DashboardCountryCard(
    modifier: Modifier = Modifier,
    country: DashboardCountryModel,
    onClick: (DashboardCountryModel) -> Unit,
    onClickActionDescription: String = stringResource(
        id = R.string.details_CD, country.name
    ),
    painter: Painter = rememberAsyncImagePainter(
        country.flagUrl ?: R.drawable.baseline_broken_image_no_padding
    ),
    iconPainter: Painter = painterResource(id = R.drawable.baseline_chevron_right_24),
    contentDescription: String = country.name
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 10.dp)
            .clickable { onClick(country) }
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
                this.customActions = listOf(
                    CustomAccessibilityAction(onClickActionDescription) {
                        onClick(country)
                        true
                    })
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier
                .size(
                    width = 60.dp,
                    height = 40.dp
                )
                .border(
                    shape = RoundedCornerShape(3.dp),
                    border = BorderStroke(1.dp, Color.Gray)
                )
                .clip(RoundedCornerShape(3.dp)),
            contentScale = ContentScale.FillBounds,
            painter = painter,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .size(10.dp)
        )
        Text(
            modifier = Modifier.weight(20f),
            text = country.name,
            style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Start),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .size(10.dp)
        )
        ProvideTextStyle(
            value = MaterialTheme.typography.labelMedium
                .copy(
                    textAlign = TextAlign.End,
                    color = MaterialTheme.typography.labelMedium.color.copy(alpha = 0.8f)
                )
        ) {
            Text(
                modifier = Modifier.weight(10f),
                text = country.capital,
                //style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.End),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Spacer(
            modifier = Modifier
                .weight(1f)
                .size(10.dp)
        )
        Icon(
            painter = iconPainter,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .align(Alignment.CenterVertically),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun preview_1() {
    DashboardCountryCard(
        country = TestCountryModelProvider.getTestCountryModel().asDashboardCountryModel()!!
            .copy(name = "Provincias Unidas del Rio de la Plata, Republica Argentina"),
        painter = painterResource(R.drawable.baseline_broken_image_no_padding),
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun preview_1_1() {
    DashboardCountryCard(
        country = TestCountryModelProvider.getTestCountryModel().asDashboardCountryModel()!!,
        painter = painterResource(R.drawable.baseline_broken_image_no_padding),
        onClick = {},
    )
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape", showBackground = true)
@Composable
private fun preview_2() {
    DashboardCountryCard(
        country = TestCountryModelProvider.getTestCountryModel().asDashboardCountryModel()!!,
        painter = painterResource(R.drawable.baseline_broken_image_no_padding),
        onClick = {},
    )
}

@Preview(device = "spec:width=600px,height=1200px,dpi=440", showBackground = true)
@Composable
private fun preview_3() {
    DashboardCountryCard(
        country = TestCountryModelProvider.getTestCountryModel().asDashboardCountryModel()!!,
        painter = painterResource(R.drawable.baseline_broken_image_no_padding),
        onClick = {},
    )
}
