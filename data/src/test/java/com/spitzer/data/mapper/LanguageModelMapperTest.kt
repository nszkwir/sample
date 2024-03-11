package com.spitzer.data.mapper
import com.spitzer.model.data.ISOLanguage
import com.spitzer.network.com.spitzer.network.model.ISOLanguageNetworkModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class LanguageModelMapperTest {

    @Test
    fun `mapNetworkModelToDataModel should map correctly`() {
        // Mock network model
        val networkModel = mockk<ISOLanguageNetworkModel> {
            every { language } returns "English"
            every { code2 } returns "en"
            every { code3 } returns "eng"
        }

        // Create an instance of the mapper
        val mapper = LanguageModelMapper()

        // Call the function under test
        val result = mapper.mapNetworkModelToDataModel(networkModel)

        // Assert the result
        val expected = ISOLanguage("English", "en", "eng")
        assertEquals(expected, result)
    }

    @Test
    fun `mapNetworkModelListToDataModels should map list correctly`() {
        // Mock network model list
        val networkList = listOf(
            mockk<ISOLanguageNetworkModel> {
                every { language } returns "English"
                every { code2 } returns "en"
                every { code3 } returns "eng"
            },
            mockk<ISOLanguageNetworkModel> {
                every { language } returns "French"
                every { code2 } returns "fr"
                every { code3 } returns "fra"
            }
            // Add more mock objects as needed
        )

        // Create an instance of the mapper
        val mapper = LanguageModelMapper()

        // Call the function under test
        val result = mapper.mapNetworkModelListToDataModels(networkList)

        // Assert the result
        val expectedList = listOf(
            ISOLanguage("English", "en", "eng"),
            ISOLanguage("French", "fr", "fra")
            // Add more expected objects as needed
        )
        assertEquals(expectedList, result)
    }
}
