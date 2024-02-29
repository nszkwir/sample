package com.spitzer.ui.tooling

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode


@Composable
fun createThemeGradient(): Brush {
    return createGradientBrush(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.tertiary
        )
    )
}

@Composable
fun createThemePrimaryColorGradient(): Brush {
    return createGradientBrush(
        listOf(
            Color.White,
            Color.White,
            Color.White,
            MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun createPrimaryToTransparentGradient(): Brush {
    return createGradientBrush(
        listOf(
            MaterialTheme.colorScheme.primary,
            Color.Transparent
        )
    )
}

fun createGradientBrush(
    colors: List<Color>,
    isVertical: Boolean = true
): Brush {

    val endOffset = if (isVertical) {
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }

    return Brush.linearGradient(
        colors = colors,
        start = Offset(0f, 0f),
        end = endOffset,
        tileMode = TileMode.Clamp
    )
}
