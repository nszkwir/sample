package com.spitzer.ui.components.transparentSearchField

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.spitzer.ui.R
import com.spitzer.ui.testing.screenshotPreview.components.TransparentSearchFieldPreview_ScreenshotTest
import com.spitzer.ui.theme.BlueGray100
import com.spitzer.ui.theme.SampleTheme

@Composable
fun TransparentSearchField(
    modifier: Modifier = Modifier,
    searchText: String? = null,
    placeholder: String = "",
    @DrawableRes searchIconDrawable: Int = R.drawable.baseline_search_24,
    @DrawableRes clearIconDrawable: Int = R.drawable.baseline_close_24,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onClearSearchText: () -> Unit,
    onSearchTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = searchText ?: "",
        placeholder = { Text(placeholder) },
        trailingIcon = {
            if (searchText.isNullOrEmpty()) {
                Icon(
                    painter = painterResource(id = searchIconDrawable),
                    contentDescription = null
                )
            } else {
                Icon(
                    modifier = Modifier.clickable { onClearSearchText() },
                    painter = painterResource(id = clearIconDrawable),
                    contentDescription = null
                )
            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            errorCursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White,
            errorTextColor = Color.White,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.White,
            disabledPlaceholderColor = Color.White,
            errorPlaceholderColor = Color.White,
            focusedPlaceholderColor = Color.White,
            unfocusedPlaceholderColor = BlueGray100,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            errorTrailingIconColor = Color.White,
            disabledTrailingIconColor = Color.White,
            focusedTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.White
        ),
        onValueChange = onSearchTextChange
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF673AB7)
@Composable
fun prev1(

) {
    SampleTheme {
        TransparentSearchFieldPreview_ScreenshotTest()
    }
}
