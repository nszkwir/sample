package com.spitzer.model.data

data class CountryDataModel(
    val cca3: String,
    val name: String,
    val flags: Flags,
    val capital: String,
    val area: Double,
    val population: Long,
) {
    data class Flags(
        val png: String?,
        val svg: String?
    )
}
