package com.spitzer.ui.layout.scaffold

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
