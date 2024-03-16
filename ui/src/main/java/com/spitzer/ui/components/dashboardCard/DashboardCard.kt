package com.spitzer.ui.components.dashboardCard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.spitzer.ui.R
import com.spitzer.ui.theme.SampleTheme

@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    imageUrl: String? = null,
    @DrawableRes imageId: Int = R.drawable.baseline_broken_image_24,
    painter: Painter = rememberAsyncImagePainter(imageUrl ?: imageId),
    contentDescription: String = title,
    isCompactMode: Boolean = true,
    leftIcon: Boolean = true,
    onCardClicked: (() -> Unit)? = null,
    onClickActionDescription: String? = null,
) {
    val newModifier = if (onCardClicked != null && onClickActionDescription?.isNotEmpty() == true) {
        modifier
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
                this.customActions = listOf(
                    CustomAccessibilityAction(onClickActionDescription) {
                        onCardClicked()
                        true
                    },
                )
            }
            .clickable { onCardClicked() }
    } else {
        modifier
    }

    OutlinedCard(
        modifier = newModifier,
    ) {
        if (isCompactMode) {
            if (leftIcon) {
                CompactLeftIconDashboardCard(title = title, subtitle = subtitle, painter = painter)
            } else {
                CompactRightIconDashboardCard(title = title, subtitle = subtitle, painter = painter)
            }
        } else {
            NormalDashboardCard(title = title, subtitle = subtitle, painter = painter)
        }
    }
}

@Composable
fun CompactRightIconDashboardCard(
    title: String,
    subtitle: String,
    painter: Painter
) {
    Row(
        modifier = Modifier
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(8f)
        ) {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    textAlign = TextAlign.End
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                modifier = Modifier.align(Alignment.End),
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.End
                ),
                minLines = 2,
                maxLines = 2
            )
        }
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

@Composable
fun CompactLeftIconDashboardCard(
    title: String,
    subtitle: String,
    painter: Painter
) {
    Row(
        modifier = Modifier
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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

        Column(
            modifier = Modifier.weight(8f)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.Start),
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Start
                ),
                minLines = 2,
                maxLines = 2
            )
        }
    }
}

@Composable
fun NormalDashboardCard(
    title: String,
    subtitle: String,
    painter: Painter
) {
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

@Preview
@Composable
fun DashboardCardPreview() {
    DashboardCardPreview_ScreenshotTest()
}

@Composable
fun DashboardCardPreview_ScreenshotTest() {
    SampleTheme {
        Column {
            DashboardCard(
                title = "Statistics",
                subtitle = "Compare and get insight about countries characteristics.",
                painter = painterResource(id = R.drawable.baseline_query_stats_24),
                contentDescription = "",
                onClickActionDescription = "",
            )
            Spacer(modifier = Modifier.size(20.dp))
            DashboardCard(
                title = "Statistics",
                subtitle = "Compare and get insight about countries characteristics.",
                painter = painterResource(id = R.drawable.baseline_query_stats_24),
                leftIcon = false,
                contentDescription = "",
                onClickActionDescription = "",
            )
            Spacer(modifier = Modifier.size(20.dp))
            DashboardCard(
                title = "Statistics",
                subtitle = "Compare and get insight about countries characteristics.",
                painter = painterResource(id = R.drawable.baseline_query_stats_24),
                isCompactMode = false,
                contentDescription = "",
                onClickActionDescription = "",
            )
        }
    }
}