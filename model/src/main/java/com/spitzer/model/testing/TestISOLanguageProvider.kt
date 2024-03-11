package com.spitzer.model.testing

import com.spitzer.model.data.ISOLanguage

object TestISOLanguageProvider {
    fun getISOLanguage(
        language: String? = null,
        code2: String? = null,
        code3: String? = null,
    ) = ISOLanguage(language = language ?: "Spanish", code2 = code2 ?: "es", code3 = code3 ?: "esp")

    fun getISOLanguageList() = listOf<ISOLanguage>(
        ISOLanguage(language = "Spanish", code2 = "es", code3 = "esp"),
        ISOLanguage(language = "English", code2 = "en", code3 = "eng"),
        ISOLanguage(language = "Dutch", code2 = "nl", code3 = "nld"),
        ISOLanguage(language = "German", code2 = "ge", code3 = "ger"),
        ISOLanguage(language = "AnExtremelyLongLanguageName", code2 = "xl", code3 = "xxl"),
    )
}
