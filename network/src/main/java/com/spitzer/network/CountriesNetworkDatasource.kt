package com.spitzer.network

import com.spitzer.network.model.CountryNetworkModel

interface CountriesNetworkDatasource {
    suspend fun getCountries(): List<CountryNetworkModel>
}
