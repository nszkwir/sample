package com.spitzer.sample.configuration

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions

@OptIn(ExperimentalRoborazziApi::class)
val DefaultRoborazziOptions =
    RoborazziOptions(
        // Pixel-perfect matching
        compareOptions = RoborazziOptions.CompareOptions(changeThreshold = 0f),
        // Reduce the size of the PNGs
        recordOptions = RoborazziOptions.RecordOptions(resizeScale = 0.5),
    )