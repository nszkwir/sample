package com.spitzer.ui.components.loadingButton

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spitzer.ui.theme.SampleTheme
import com.spitzer.ui.tooling.PhonePreview

@Composable
fun OutlinedLoadingButton(
    modifier: Modifier = Modifier,
    text: String,
    contentDescription: String? = null,
    state: LoadingButtonState = LoadingButtonState.IDLE,
    @DrawableRes idleDrawable: Int? = null,
    onClick: () -> Unit = {},
) {
    // Color animation
    val stateTransition = updateTransition(targetState = state, label = "colorTransition")
    val colorAnimation by stateTransition.animateColor(
        transitionSpec = { tween(durationMillis = 500) },
        label = "colorAnimation",
        targetValueByState = {
            it.getColor()
        }
    )
    // Icon resource
    val targetIconResource = state.getDrawableId(idleDrawable)
    // Rotation animation
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val infiniteRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = state.getRotation(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        ), label = "infiniteRotation"
    )

    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(ButtonDefaults.outlinedButtonBorder.width, colorAnimation),
        onClick = {
            if (state == LoadingButtonState.IDLE) onClick()
        }
    ) {
        ProvideTextStyle(value = TextStyle.Default.copy(color = colorAnimation)) {
            Text(text = text)
        }
        Spacer(modifier = Modifier.width(20.dp))
        // Icon transition
        Crossfade(
            targetState = targetIconResource,
            animationSpec = tween(durationMillis = 500),
            label = "iconTransition"
        ) { drawableId ->
            Image(
                modifier = Modifier.graphicsLayer(rotationZ = infiniteRotation),
                painter = painterResource(id = drawableId),
                colorFilter = ColorFilter.tint(color = colorAnimation),
                contentDescription = state.getContentDescription(contentDescription),
            )
        }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait", showBackground = true)
@Composable
fun OutlinedLoadingButtonPreview() {
    OutlinedLoadingButtonPreview_ScreenshotTest()
}

@Composable
fun OutlinedLoadingButtonPreview_ScreenshotTest() {
    SampleTheme {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            OutlinedLoadingButton(text = "Button")
            OutlinedLoadingButton(text = "Button", state = LoadingButtonState.IN_PROGRESS)
            OutlinedLoadingButton(text = "Button", state = LoadingButtonState.SUCCESS)
            OutlinedLoadingButton(text = "Button", state = LoadingButtonState.ERROR)
        }
    }
}
