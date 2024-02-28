package com.spitzer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LightAndroidGradientColors = GradientColors(container = BlueGray100)
val DarkAndroidGradientColors = GradientColors(container = Color.Black)

val LightAndroidBackgroundTheme = BackgroundTheme(color = BlueGray100)
val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)

@Composable
fun SampleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }
    // Gradient colors
    val gradientColors = if (darkTheme) DarkAndroidGradientColors else LightAndroidGradientColors
    // Background
    val backgroundTheme = if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides TintTheme(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = SampleTypography,
            content = content
        )
    }
}
