package com.spitzer.ui.testing.screenshotPreview.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spitzer.ui.R
import com.spitzer.ui.layout.scaffold.topappbar.LargeTopAppBar
import com.spitzer.ui.layout.scaffold.topappbar.LargeTopAppBarConfiguration
import com.spitzer.ui.theme.SampleTheme

@Composable
fun LTABLayout_ScreenshotTest() {
    SampleTheme {
        Column(modifier = Modifier.padding(5.dp)) {
            LargeTopAppBar(configuration = LargeTopAppBarConfiguration())
            Spacer(modifier = Modifier.size(5.dp))
            LargeTopAppBar(configuration = LargeTopAppBarConfiguration(title = "LargeTopAppBar"))
            Spacer(modifier = Modifier.size(5.dp))
            LargeTopAppBar(configuration = LargeTopAppBarConfiguration(
                buttonIconId = R.drawable.baseline_settings_24,
                onButtonClicked = {}
            ))
            Spacer(modifier = Modifier.size(5.dp))
            LargeTopAppBar(configuration = LargeTopAppBarConfiguration(
                navIconId = R.drawable.baseline_settings_24,
                onNavIconClicked = {}
            ))
            Spacer(modifier = Modifier.size(5.dp))
            LargeTopAppBar(configuration = LargeTopAppBarConfiguration(
                title = "LargeTopAppBar",
                buttonIconId = R.drawable.baseline_settings_24,
                onButtonClicked = {},
                navIconId = R.drawable.baseline_arrow_back_24,
                onNavIconClicked = {}
            ))
        }
    }
}

@Composable
fun LTABLayout_ScreenshotTest2() {
    SampleTheme {
        Column(modifier = Modifier.padding(5.dp)) {
            LargeTopAppBar(configuration = LargeTopAppBarConfiguration(
                title = "This is a really long title for this LargeTopAppBar",
                buttonIconId = R.drawable.baseline_settings_24,
                onButtonClicked = {},
                navIconId = R.drawable.baseline_arrow_back_24,
                onNavIconClicked = {}
            ))
            Spacer(modifier = Modifier.size(5.dp))
            LargeTopAppBar(
                configuration = LargeTopAppBarConfiguration(
                    title = "LargeTopAppBar custom colors",
                    buttonIconId = R.drawable.baseline_settings_24,
                    onButtonClicked = {},
                    navIconId = R.drawable.baseline_arrow_back_24,
                    onNavIconClicked = {},
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    elementsColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}
