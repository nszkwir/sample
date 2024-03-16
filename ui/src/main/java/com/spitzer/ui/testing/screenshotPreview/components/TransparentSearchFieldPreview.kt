package com.spitzer.ui.testing.screenshotPreview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spitzer.ui.R
import com.spitzer.ui.components.transparentSearchField.TransparentSearchField
import com.spitzer.ui.theme.SampleTheme

@Composable
fun TransparentSearchFieldPreview_ScreenshotTest() {
    SampleTheme {
        Column(modifier = Modifier.padding(5.dp)) {
            TransparentSearchField(onClearSearchText = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            TransparentSearchField(searchText = "Arg", onClearSearchText = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            TransparentSearchField(placeholder = "country: ", onClearSearchText = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            TransparentSearchField(
                placeholder = "country: ",
                searchText = "Argent",
                onClearSearchText = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            TransparentSearchField(
                onClearSearchText = {},
                searchIconDrawable = R.drawable.baseline_error_outline_24
            ) {}
            Spacer(modifier = Modifier.size(5.dp))
            TransparentSearchField(
                searchText = "Arg",
                onClearSearchText = {},
                clearIconDrawable = R.drawable.baseline_settings_24
            ) {}
        }
    }
}
