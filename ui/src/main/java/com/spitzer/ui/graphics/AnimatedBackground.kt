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
    p1HeightWeight: Float = 0.35f,
    p1FinalHeightWeight: Float = 0.25f,
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
        drawPath(
            path = getThreePointsPath(size.height, size.width, p1Y.value),
            color = shapeColor
        )
    }
}

private fun getTwoPointsPath(height: Float, width: Float, p1Y: Float): Path {
    val startingH = height * p1Y
    val startingW = 0f
    val endingH = height * p1Y * 1.45f

    val x1 = width / 3f
    val cx1 = x1 / 2f
    val y1 = startingH * 1.05f
    val cy1 = y1 * 1.25f

    val x2 = width
    val cx2 = (x2 / 3f) * 2
    val y2 = endingH
    val cy2 = endingH * 0.5f

    return Path().apply {
        moveTo(0f, 0f)
        lineTo(startingW, startingH)
        quadraticBezierTo(cx1, cy1, x1, y1)
        quadraticBezierTo(cx2, cy2, x2, y2)
        lineTo(width, 0f)
        lineTo(0f, 0f)
        close()
    }
}


private fun getThreePointsInvertedPath(height: Float, width: Float, p1Y: Float): Path {
    val startingH = height * p1Y
    val startingW = 0f
    val endingH = height * p1Y * 1.1f

    val x1 = width / 3f
    val cx1 = x1 / 2f
    val y1 = startingH * 1.35f
    val cy1 = y1 * 1.25f

    val x2 = width / 2f
    val cx2 = x1 + ((x2 - x1) / 2f)
    val y2 = y1
    val cy2 = y2 * 0.85f

    val x3 = width
    val cx3 = x2 + ((width - x2) / 2f)
    val y3 = y2 * 0.9f
    val cy3 = y2 * 1.41f

    return Path().apply {
        moveTo(0f, 0f)
        lineTo(startingW, startingH)
        quadraticBezierTo(cx1, cy1, x1, y1)
        quadraticBezierTo(cx2, cy2, x2, y2)
        quadraticBezierTo(cx3, cy3, x3, y3)
        lineTo(width, 0f)
        lineTo(0f, 0f)
        close()
    }
}

private fun getThreePointsPath(height: Float, width: Float, p1Y: Float): Path {
    val startingH = height * p1Y
    val startingW = 0f
    val endingH = height * p1Y * 1.1f

    val x1 = width / 2f
    val cx1 = x1 / 2f * 0.9f
    val y1 = startingH * 1.25f
    val cy1 = y1 * 1.45f

    val x2 = width * 7f / 10f
    val cx2 = x1 + ((x2 - x1) / 2f)
    val y2 = y1
    val cy2 = y2 * 0.85f

    val x3 = width
    val cx3 = x2 + ((width - x2) / 2f) * 1.2f
    val y3 = y2 * 0.77f
    val cy3 = y2 * 1.3f

    return Path().apply {
        moveTo(0f, 0f)
        lineTo(startingW, startingH)
        quadraticBezierTo(cx1, cy1, x1, y1)
        quadraticBezierTo(cx2, cy2, x2, y2)
        quadraticBezierTo(cx3, cy3, x3, y3)
        lineTo(width, 0f)
        lineTo(0f, 0f)
        close()
    }
}
