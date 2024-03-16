package com.spitzer.ui.components.transparentSearchField

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spitzer.ui.R
import com.spitzer.ui.theme.SampleTheme

@Composable
fun PrimaryOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String? = null,
    placeholder: String = "",
    @DrawableRes trailingIcon: Int?,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onTrailingIconClicked: () -> Unit,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text ?: "",
        placeholder = { Text(placeholder) },
        trailingIcon = {
            if (trailingIcon != null) {
                Icon(
                    modifier = Modifier.clickable { onTrailingIconClicked() },
                    painter = painterResource(id = trailingIcon),
                    contentDescription = null
                )
            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = Color.White,
            disabledContainerColor = Color.White
        ),
        onValueChange = onTextChanged
    )
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait", showBackground = true)
@Composable
fun preview() {
    PrimaryOutlinedTexFieldPreview_ScreenshotTest()
}

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
