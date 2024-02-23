package com.spitzer.ui.layout.scaffold

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
            shape = CircleShape,
            containerColor = Color.Blue.copy(alpha = 0.7f),
            onClick = { configuration.onFABClicked.invoke() }
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = configuration.fabIconId),
                contentDescription = configuration.fabContentDescription,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}
