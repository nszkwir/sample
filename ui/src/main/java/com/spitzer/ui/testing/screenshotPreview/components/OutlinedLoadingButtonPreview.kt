package com.spitzer.ui.testing.screenshotPreview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spitzer.ui.components.loadingButton.LoadingButtonState
import com.spitzer.ui.components.loadingButton.OutlinedLoadingButton
import com.spitzer.ui.theme.SampleTheme

@Composable
fun OutlinedLoadingButtonPreview_ScreenshotTest() {
    SampleTheme {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            OutlinedLoadingButton(text = "Button")
            OutlinedLoadingButton(text = "Button", state = LoadingButtonState.IN_PROGRESS)
            OutlinedLoadingButton(text = "Button", state = LoadingButtonState.SUCCESS)
            OutlinedLoadingButton(text = "Button", state = LoadingButtonState.ERROR)
        }
    }
}
