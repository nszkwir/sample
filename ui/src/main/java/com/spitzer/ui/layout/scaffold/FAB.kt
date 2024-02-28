package com.spitzer.ui.layout.scaffold

import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

data class FABConfiguration(
    @DrawableRes val fabIconId: Int? = null,
    val onFABClicked: (() -> Unit)? = null,
    val fabContentDescription: String? = null,
)

@Composable
fun FABLayout(
    modifier: Modifier = Modifier,
    configuration: FABConfiguration
) {
    if (configuration.fabIconId != null && configuration.onFABClicked != null) {
        FloatingActionButton(
            modifier = modifier,
            shape = CircleShape,
            onClick = { configuration.onFABClicked.invoke() }
        ) {
            Icon(
                painter = painterResource(id = configuration.fabIconId),
                contentDescription = configuration.fabContentDescription,
            )
        }
    }
}
