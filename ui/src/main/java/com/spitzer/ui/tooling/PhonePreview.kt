package com.spitzer.ui.tooling

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "phone",
    device = "spec:shape=Normal,width=360,unit=dp,dpi=480",
    backgroundColor = 0xFFFFFEFE,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true
)
annotation class PhonePreview
