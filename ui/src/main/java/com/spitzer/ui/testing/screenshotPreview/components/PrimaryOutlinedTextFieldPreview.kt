package com.spitzer.ui.testing.screenshotPreview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spitzer.ui.R
import com.spitzer.ui.components.transparentSearchField.PrimaryOutlinedTextField
import com.spitzer.ui.theme.SampleTheme

@Composable
fun PrimaryOutlinedTexFieldPreview_ScreenshotTest() {
    SampleTheme {
        Column(modifier = Modifier.padding(5.dp)) {
            PrimaryOutlinedTextField(trailingIcon = null, onTrailingIconClicked = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            PrimaryOutlinedTextField(
                placeholder = "example: ",
                trailingIcon = null,
                onTrailingIconClicked = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            PrimaryOutlinedTextField(
                trailingIcon = R.drawable.baseline_edit_24,
                onTrailingIconClicked = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            Spacer(modifier = Modifier.size(5.dp))
            PrimaryOutlinedTextField(
                placeholder = "example:",
                trailingIcon = R.drawable.baseline_edit_24,
                onTrailingIconClicked = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            PrimaryOutlinedTextField(
                text = "Hello",
                trailingIcon = null,
                onTrailingIconClicked = {}) {}
            Spacer(modifier = Modifier.size(5.dp))
            PrimaryOutlinedTextField(
                text = "Hello",
                trailingIcon = R.drawable.baseline_edit_24,
                onTrailingIconClicked = {}) {}
        }
    }
}
