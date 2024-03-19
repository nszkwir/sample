package com.spitzer.sample.configuration

import androidx.compose.ui.test.SemanticsNodeInteraction
import com.github.takahirom.roborazzi.captureRoboImage

fun SemanticsNodeInteraction.captureRoborazziImage(screenshotName: String, absolutePath: Boolean = false) {
    val filePath =
        if (absolutePath) "${screenshotName}.png" else "${screenshotsHomePath}${screenshotName}.png"
    this.captureRoboImage(filePath, roborazziOptions = DefaultRoborazziOptions)
}
