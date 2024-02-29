package com.spitzer.ui.layout.scaffold.topbar

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

data class SampleTopAppBarConfiguration(
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
    configuration: SampleTopAppBarConfiguration
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
