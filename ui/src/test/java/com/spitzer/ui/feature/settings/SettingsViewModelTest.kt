package com.spitzer.ui.feature.settings

import app.cash.turbine.test
import com.spitzer.common.database.TransactionState
import com.spitzer.common.testing.di.MainDispatcherRule
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.ui.components.loadingButton.toLoadingButtonState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SettingsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    // Mocks
    private val countriesRepository: CountriesRepository = mockk(relaxed = true)
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        viewModel = SettingsViewModel(countriesRepository)
    }

    @Test
    fun `initializing viemodel sets uiState with default values`() = runTest {
        assertEquals(actual = viewModel.uiState.value, expected = SettingsUiState())
    }

    @Test
    fun `collecting TransactionState when updateCountries updates uiState`() = runTest {
        val state1 = TransactionState.IN_PROGRESS
        val state2 = TransactionState.SUCCESS
        val state3 = TransactionState.IDLE

        // Mock repository behavior
        coEvery { countriesRepository.restoreCountriesFlow() } returns flow {
            emit(state1)
            emit(state2)
            emit(state3)
        }

        viewModel.uiState.test {
            awaitEvent()
            assertEquals(
                actual = viewModel.uiState.value.restoreButtonState,
                expected = TransactionState.IDLE.toLoadingButtonState()
            )
            viewModel.updateCountries()

            awaitEvent()
            assertEquals(
                actual = viewModel.uiState.value.restoreButtonState,
                expected = state1.toLoadingButtonState()
            )

            awaitEvent()
            assertEquals(
                actual = viewModel.uiState.value.restoreButtonState,
                expected = state2.toLoadingButtonState()
            )

            awaitEvent()
            assertEquals(
                actual = viewModel.uiState.value.restoreButtonState,
                expected = state3.toLoadingButtonState()
            )
        }
        coVerify { countriesRepository.restoreCountriesFlow() }
    }

    @Test
    fun `updateCountries do nothing when transactionState is not IDLE`() = runTest {
        val state1 = TransactionState.IN_PROGRESS
        val state2 = TransactionState.SUCCESS

        // Mock repository behavior
        coEvery { countriesRepository.restoreCountriesFlow() } returns flow {
            emit(state1)
            emit(state2)
        }

        viewModel.uiState.test {
            awaitEvent()
            assertEquals(
                actual = viewModel.uiState.value.restoreButtonState,
                expected = TransactionState.IDLE.toLoadingButtonState()
            )
            viewModel.updateCountries()

            awaitEvent()
            assertEquals(
                actual = viewModel.uiState.value.restoreButtonState,
                expected = state1.toLoadingButtonState()
            )

            awaitEvent()
            assertEquals(
                actual = viewModel.uiState.value.restoreButtonState,
                expected = state2.toLoadingButtonState()
            )

            viewModel.updateCountries()

            expectNoEvents()

            assertEquals(
                actual = viewModel.uiState.value.restoreButtonState,
                expected = state2.toLoadingButtonState()
            )
        }
        coVerify { countriesRepository.restoreCountriesFlow() }
    }
}
