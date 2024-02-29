package com.spitzer.ui.graphics

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun SystemUiController.configureWindowDecoration(
    setDecorFitsSystemWindows: Boolean = true,
    useDarkIcons: Boolean = !isSystemInDarkTheme(),
    statusBarColor: Color = Color.Transparent
) {
    LocalContext.current.setDecorFitsSystemWindows(setDecorFitsSystemWindows)
    LaunchedEffect(Unit) {
        this@configureWindowDecoration.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }
}

@Composable
fun ConfigureWindowDecoration(
    setDecorFitsSystemWindows: Boolean = true,
    useDarkIcons: Boolean = !isSystemInDarkTheme(),
    statusBarColor: Color = Color.Transparent
) {
    LocalContext.current.setDecorFitsSystemWindows(setDecorFitsSystemWindows)
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }
}

fun Context.setDecorFitsSystemWindows(value: Boolean) {
    this.findActivity()?.let { activity ->
        activity.window?.let { window ->
            WindowCompat.setDecorFitsSystemWindows(window, value)
        }
    }
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}