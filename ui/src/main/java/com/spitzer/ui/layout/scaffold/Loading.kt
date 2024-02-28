package com.spitzer.ui.layout.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingLayout(
    modifier: Modifier = Modifier
) {
    // used to remove ripple effect from loading background
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.7f))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                // do nothing, it's clickable to prevent the user to interact with
                // the content while loading
            }
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
