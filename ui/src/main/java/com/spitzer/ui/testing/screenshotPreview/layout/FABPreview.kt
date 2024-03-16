package com.spitzer.ui.testing.screenshotPreview.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spitzer.ui.R
import com.spitzer.ui.layout.scaffold.FABConfiguration
import com.spitzer.ui.layout.scaffold.FABLayout
import com.spitzer.ui.theme.SampleTheme

@Composable
fun FABLayout_ScreenshotTest() {
    SampleTheme {
        Column(modifier = Modifier.padding(5.dp)) {
            FABLayout(configuration = FABConfiguration())
            Spacer(modifier = Modifier.size(5.dp))
            FABLayout(configuration = FABConfiguration(
                fabIconId = R.drawable.baseline_add_24,
                onFABClicked = {}
            ))
            Spacer(modifier = Modifier.size(5.dp))
            FABLayout(configuration = FABConfiguration(
                fabIconId = R.drawable.baseline_close_24,
                onFABClicked = {}
            ))
        }
    }
}
