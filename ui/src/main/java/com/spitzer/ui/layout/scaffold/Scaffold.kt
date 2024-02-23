package com.spitzer.ui.layout.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun ScaffoldLayout(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    isLoading: Boolean = false,
    topBarContent: @Composable () -> Unit,
    fabContent: @Composable () -> Unit,
    loadingContent: @Composable (Dp) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(color = backgroundColor),
        topBar = topBarContent,
        floatingActionButton = fabContent
    )
    {
        val paddingTop = it.calculateTopPadding()
        Box(
            modifier = Modifier.padding(top = paddingTop)
        ) {
            content()
        }

        if (isLoading) loadingContent(paddingTop)
    }
}
