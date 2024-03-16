package com.spitzer.ui.layout.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.spitzer.ui.testing.screenshotPreview.layout.ScaffoldLayout_ScreenshotTest
import com.spitzer.ui.testing.screenshotPreview.layout.ScaffoldLayout_ScreenshotTest_LargeTAB

@Composable
fun ScaffoldLayout(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    isLoading: Boolean = false,
    backgroundContent: @Composable () -> Unit = {},
    topBarContent: @Composable () -> Unit = {},
    fabContent: @Composable () -> Unit = {},
    loadingContent: @Composable (Dp) -> Unit = {},
    content: @Composable () -> Unit
) {
    backgroundContent()
    Scaffold(
        modifier = modifier,
        topBar = topBarContent,
        containerColor = Color.Transparent,
        floatingActionButton = fabContent
    )
    {
        val paddingTop = it.calculateTopPadding()
        Box(modifier = contentModifier.padding(top = paddingTop)) {
            content()
        }
        if (isLoading) loadingContent(paddingTop)
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait", showBackground = true)
@Composable
fun ScaffoldLayoutPreview() {
    ScaffoldLayout_ScreenshotTest()
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait", showBackground = true)
@Composable
fun ScaffoldLayoutPreview2() {
    ScaffoldLayout_ScreenshotTest_LargeTAB()
}
