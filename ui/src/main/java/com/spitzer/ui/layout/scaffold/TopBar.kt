package com.spitzer.ui.layout.scaffold

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class TopBarConfiguration(
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
fun TopBarLayout(
    modifier: Modifier = Modifier,
    configuration: TopBarConfiguration
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                color = Color.White,
                text = configuration.title ?: ""
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue.copy(alpha = 0.7f)
        ),
        navigationIcon = {
            if (configuration.navIconId != null && configuration.onNavIconClicked != null)
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clickable {
                            configuration.onNavIconClicked.invoke()
                        },
                    painter = painterResource(id = configuration.navIconId),
                    contentDescription = configuration.navIconContentDescription,
                    colorFilter = ColorFilter.tint(Color.White)
                )
        },
        actions = {
            if (configuration.buttonIconId != null && configuration.onButtonClicked != null)
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clickable {
                            configuration.onButtonClicked.invoke()
                        },
                    painter = painterResource(id = configuration.buttonIconId),
                    contentDescription = configuration.buttonContentDescription,
                    colorFilter = ColorFilter.tint(Color.White)
                )
        },
    )
}

