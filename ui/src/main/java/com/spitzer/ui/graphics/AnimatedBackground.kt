package com.spitzer.ui.graphics

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path

@Composable
fun AnimatedBackground(
    modifier: Modifier = Modifier,
    p1HeightWeight: Float = 0.28f,
    p1FinalHeightWeight: Float = 0.26f,
    animate: Boolean = false
) {
    val shapeColor = MaterialTheme.colorScheme.primary

    val p1Y = remember { Animatable(p1HeightWeight) }
    val animationDuration = if (animate) 600 else 0

    LaunchedEffect(Unit) {
        p1Y.animateTo(
            targetValue = p1FinalHeightWeight,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val contentH = size.height
        val contentW = size.width

        val startingH = contentH * p1Y.value
        val startingW = 0f
        val endingH = contentH * p1Y.value * 1.45f

        val x1 = contentW / 3f
        val cx1 = x1 / 2f
        val y1 = startingH * 1.05f
        val cy1 = y1 * 1.25f

        val x2 = contentW
        val cx2 = (x2 / 3f) * 2
        val y2 = endingH
        val cy2 = endingH * 0.5f

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(startingW, startingH)
            quadraticBezierTo(cx1, cy1, x1, y1)
            quadraticBezierTo(cx2, cy2, x2, y2)
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
            close()
        }
        drawPath(
            path = path,
            color = shapeColor
        )
    }
}
