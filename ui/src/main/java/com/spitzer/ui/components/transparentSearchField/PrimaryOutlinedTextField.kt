package com.spitzer.ui.components.transparentSearchField

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun PrimaryOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String? = null,
    placeholder: String = "",
    @DrawableRes trailingIcon: Int?,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onTrailingIconClicked: () -> Unit,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text ?: "",
        placeholder = { Text(placeholder) },
        trailingIcon = {
            if (trailingIcon != null) {
                Icon(
                    modifier = Modifier.clickable { onTrailingIconClicked() },
                    painter = painterResource(id = trailingIcon),
                    contentDescription = null
                )
            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = Color.White,
            disabledContainerColor = Color.White
        ),
        onValueChange = onTextChanged
    )
}