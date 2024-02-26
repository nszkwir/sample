package com.spitzer.ui.tooling

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multipreview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 */
@Preview(
    name = "phone",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    backgroundColor = 0xFFFFFEFE,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true
)
@Preview(
    name = "landscape",
    device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480",
    backgroundColor = 0xFFFFFFFF
)
@Preview(
    name = "foldable",
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
    backgroundColor = 0xFFFFFF
)
@Preview(
    name = "tablet",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
    backgroundColor = 0xFFFFFFFF
)
annotation class DevicePreviews
