package com.spitzer.ui.layout.scaffold.topappbar

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.spitzer.ui.testing.screenshotPreview.layout.LTABLayout_ScreenshotTest
import com.spitzer.ui.testing.screenshotPreview.layout.LTABLayout_ScreenshotTest2

data class LargeTopAppBarConfiguration(
    val title: String? = "",
    @ColorRes val backgroundColor: Color = Color.Unspecified,
    @ColorRes val elementsColor: Color = Color.Unspecified,
    @DrawableRes val navIconId: Int? = null,
    val onNavIconClicked: (() -> Unit)? = null,
    val navIconContentDescription: String? = null,
    @DrawableRes val buttonIconId: Int? = null,
    val onButtonClicked: (() -> Unit)? = null,
    val buttonContentDescription: String? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeTopAppBar(
    modifier: Modifier = Modifier,
    configuration: LargeTopAppBarConfiguration
) {
    LargeTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = configuration.backgroundColor),
        title = {
            ProvideTextStyle(value = TextStyle.Default.copy(color = configuration.elementsColor)) {
                Text(
                    text = configuration.title ?: ""
                )
            }
        },
        navigationIcon = {
            if (configuration.navIconId != null && configuration.onNavIconClicked != null)
                IconButton(onClick = { configuration.onNavIconClicked.invoke() }) {
                    Icon(
                        tint = configuration.elementsColor,
                        painter = painterResource(id = configuration.navIconId),
                        contentDescription = configuration.navIconContentDescription,
                    )
                }
        },
        actions = {
            if (configuration.buttonIconId != null && configuration.onButtonClicked != null)
                IconButton(onClick = { configuration.onButtonClicked.invoke() }) {
                    Icon(
                        tint = configuration.elementsColor,
                        painter = painterResource(id = configuration.buttonIconId),
                        contentDescription = configuration.buttonContentDescription,
                    )
                }
        },
    )
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait", showBackground = true)
@Composable
fun LTABLayoutPreview() {
    LTABLayout_ScreenshotTest()
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait", showBackground = true)
@Composable
fun LTABLayoutPreview2() {
    LTABLayout_ScreenshotTest2()
}
