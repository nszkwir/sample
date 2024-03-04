package com.spitzer.network.com.spitzer.network.mapper

interface NetworkMapper<N, M> {
    fun mapToModel(networkModel: N): M?
}
