package com.spitzer.ui.components.loadingButton

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.spitzer.common.database.TransactionState
import com.spitzer.ui.R

enum class LoadingButtonState {
    IDLE, IN_PROGRESS, SUCCESS, ERROR
}

@Composable
fun LoadingButtonState.getColor() =
    when (this) {
        LoadingButtonState.IDLE -> MaterialTheme.colorScheme.outline
        LoadingButtonState.IN_PROGRESS -> Color.Gray
        LoadingButtonState.SUCCESS -> Color.Green
        LoadingButtonState.ERROR -> MaterialTheme.colorScheme.error
    }

fun LoadingButtonState.getDrawableId(idleDrawableId: Int?) =
    when (this) {
        LoadingButtonState.IDLE -> idleDrawableId ?: R.drawable.baseline_restore_24
        LoadingButtonState.IN_PROGRESS -> R.drawable.baseline_sync_24
        LoadingButtonState.SUCCESS -> R.drawable.baseline_check_24
        LoadingButtonState.ERROR -> R.drawable.baseline_error_outline_24
    }

@Composable
fun LoadingButtonState.getContentDescription(contentDescription: String?) =
    when (this) {
        LoadingButtonState.IN_PROGRESS -> contentDescription + stringResource(id = R.string.loading)
        else -> contentDescription
    }

fun LoadingButtonState.getRotation() =
    when (this) {
        LoadingButtonState.IN_PROGRESS -> 360f
        else -> 0f
    }

fun TransactionState.toLoadingButtonState() =
    when (this) {
        TransactionState.IDLE -> LoadingButtonState.IDLE
        TransactionState.IN_PROGRESS -> LoadingButtonState.IN_PROGRESS
        TransactionState.SUCCESS -> LoadingButtonState.SUCCESS
        TransactionState.ERROR -> LoadingButtonState.ERROR
    }

