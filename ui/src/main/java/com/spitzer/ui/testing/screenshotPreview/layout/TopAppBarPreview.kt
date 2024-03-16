package com.spitzer.ui.testing.screenshotPreview.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spitzer.ui.R
import com.spitzer.ui.layout.scaffold.topappbar.TopAppBar
import com.spitzer.ui.layout.scaffold.topappbar.TopAppBarConfiguration
import com.spitzer.ui.theme.SampleTheme

@Composable
fun TABLayout_ScreenshotTest() {
    SampleTheme {
        Column(modifier = Modifier.padding(5.dp)) {
            TopAppBar(configuration = TopAppBarConfiguration())
            Spacer(modifier = Modifier.size(5.dp))
            TopAppBar(configuration = TopAppBarConfiguration(title = "TopAppBar"))
            Spacer(modifier = Modifier.size(5.dp))
            TopAppBar(configuration = TopAppBarConfiguration(
                buttonIconId = R.drawable.baseline_settings_24,
                onButtonClicked = {}
            ))
            Spacer(modifier = Modifier.size(5.dp))
            TopAppBar(configuration = TopAppBarConfiguration(
                navIconId = R.drawable.baseline_settings_24,
                onNavIconClicked = {}
            ))
            Spacer(modifier = Modifier.size(5.dp))
            TopAppBar(configuration = TopAppBarConfiguration(
                title = "TopAppBar",
                buttonIconId = R.drawable.baseline_settings_24,
                onButtonClicked = {},
                navIconId = R.drawable.baseline_arrow_back_24,
                onNavIconClicked = {}
            ))
            Spacer(modifier = Modifier.size(5.dp))
            TopAppBar(configuration = TopAppBarConfiguration(
                title = "This is a really long title for this TopAppBar",
                buttonIconId = R.drawable.baseline_settings_24,
                onButtonClicked = {},
                navIconId = R.drawable.baseline_arrow_back_24,
                onNavIconClicked = {}
            ))
        }
    }
}
