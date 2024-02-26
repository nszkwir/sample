package com.spitzer.ui.components.loadingButton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    val color = state.getColor()
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors().copy(contentColor = Color.Red),
        border = BorderStroke(ButtonDefaults.outlinedButtonBorder.width, color),
        onClick = {
            if (state == LoadingButtonState.IDLE) onClick()
        }
    ) {
        Text(text = text, color = color)
        Spacer(modifier = Modifier.width(20.dp))
        Image(
            painter = painterResource(id = state.getDrawableId(idleDrawable)),
            colorFilter = ColorFilter.tint(color = color),
            contentDescription = state.getContentDescription(contentDescription)
        )
    }
}

@PhonePreview
@Composable
fun OutlinedLoadingButtonPreview() {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        OutlinedLoadingButton(text = "Button")
        OutlinedLoadingButton(text = "Button", state = LoadingButtonState.IN_PROGRESS)
        OutlinedLoadingButton(text = "Button", state = LoadingButtonState.SUCCESS)
        OutlinedLoadingButton(text = "Button", state = LoadingButtonState.ERROR)
    }
}
