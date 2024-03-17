package com.spitzer.sample.configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.testharness.TestHarness

fun ComposeContentTestRule.setContentAndCapture(
    screenshotName: String,
    deviceName: String = "Pixel6",
    darkMode: Boolean = false,
    fontScale: FontScale = FontScale.ONE_TO_ONE,
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    content: @Composable () -> Unit
) {
    this.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) { TestHarness(fontScale = fontScale.value, darkMode = darkMode) { content() } }
    }

    this.onRoot().captureRoboImage(
        getScreenshotFilePath(screenshotName, deviceName, darkMode, fontScale.description),
        roborazziOptions = roborazziOptions
    )
}
