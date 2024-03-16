package com.spitzer.ui.testing.screenshotPreview.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spitzer.ui.layout.scaffold.LoadingLayout
import com.spitzer.ui.theme.SampleTheme

@Composable
fun LoadingLayout_ScreenshotTest() {
    SampleTheme {
        Column(modifier = Modifier.padding(5.dp)){
            LoadingLayout(contentDescription = "Loading")
        }
    }
}
