package com.spitzer.network

import com.spitzer.network.model.CountryInfoNetworkModel

interface CountriesNetworkDatasource {
    suspend fun getCountriesInfo(): List<CountryInfoNetworkModel>
}
