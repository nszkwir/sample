package com.spitzer.ui.testing.screenshotPreview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spitzer.ui.R
import com.spitzer.ui.components.dashboardCard.DashboardCard
import com.spitzer.ui.theme.SampleTheme

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
