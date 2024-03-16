package com.spitzer.ui.layout.scaffold.topappbar

import androidx.annotation.DrawableRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.spitzer.ui.testing.screenshotPreview.layout.TABLayout_ScreenshotTest

data class TopAppBarConfiguration(
    val title: String? = "",
    @DrawableRes val navIconId: Int? = null,
    val onNavIconClicked: (() -> Unit)? = null,
    val navIconContentDescription: String? = null,
    @DrawableRes val buttonIconId: Int? = null,
    val onButtonClicked: (() -> Unit)? = null,
    val buttonContentDescription: String? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    configuration: TopAppBarConfiguration
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = configuration.title ?: "",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (configuration.navIconId != null && configuration.onNavIconClicked != null)
                IconButton(onClick = { configuration.onNavIconClicked.invoke() }) {
                    Icon(
                        painter = painterResource(id = configuration.navIconId),
                        contentDescription = configuration.navIconContentDescription,
                    )
                }
        },
        actions = {
            if (configuration.buttonIconId != null && configuration.onButtonClicked != null)
                IconButton(onClick = { configuration.onButtonClicked.invoke() }) {
                    Icon(
                        painter = painterResource(id = configuration.buttonIconId),
                        contentDescription = configuration.buttonContentDescription,
                    )
                }
        },
    )
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait", showBackground = true)
@Composable
fun TABLayoutPreview() {
    TABLayout_ScreenshotTest()
}
