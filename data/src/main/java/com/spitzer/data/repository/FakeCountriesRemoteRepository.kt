package com.spitzer.data.repository

import com.spitzer.common.database.TransactionState
import kotlinx.coroutines.flow.Flow

interface FakeCountriesRemoteRepository {
    suspend fun updateCountries(): Flow<TransactionState>

}
