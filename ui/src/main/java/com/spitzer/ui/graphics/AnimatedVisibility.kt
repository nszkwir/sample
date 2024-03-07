package com.spitzer.ui.graphics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

@Composable
fun AnimateSlideFromLeft(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    durationMillis: Int = 200,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideIn(
            initialOffset = { IntOffset(-it.width, 0) },
            animationSpec = tween(durationMillis, 0, LinearEasing)
        ),
        exit = slideOut(
            targetOffset = { IntOffset(-it.width, 0) },
            animationSpec = tween(durationMillis, 0, LinearEasing)
        ),
        content = content
    )
}

@Composable
fun AnimateSlideFromRight(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    durationMillis: Int = 200,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideIn(
            initialOffset = { IntOffset(it.width, 0) },
            animationSpec = tween(durationMillis, 0, LinearEasing)
        ),
        exit = slideOut(
            targetOffset = { IntOffset(it.width, 0) },
            animationSpec = tween(durationMillis, 0, LinearEasing)
        ),
        content = content
    )
}

@Composable
fun AnimateSlideFromBottom(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    durationMillis: Int = 200,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideIn(
            initialOffset = { IntOffset(0, it.height) },
            animationSpec = tween(durationMillis, 0, LinearEasing)
        ),
        exit = slideOut(
            targetOffset = { IntOffset(0, it.height) },
            animationSpec = tween(durationMillis, 0, LinearEasing)
        ),
        content = content
    )
}
