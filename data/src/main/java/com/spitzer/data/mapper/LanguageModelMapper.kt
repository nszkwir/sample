package com.spitzer.data.mapper

import com.spitzer.model.data.ISOLanguage
import com.spitzer.network.com.spitzer.network.model.ISOLanguageNetworkModel
import com.spitzer.network.com.spitzer.network.model.asDataModel
import javax.inject.Inject

class LanguageModelMapper @Inject constructor(

) {
    fun mapNetworkModelToDataModel(networkModel: ISOLanguageNetworkModel): ISOLanguage {
        return networkModel.asDataModel()
    }

    fun mapNetworkModelListToDataModels(networkList: List<ISOLanguageNetworkModel>): List<ISOLanguage> {
        return networkList.map { mapNetworkModelToDataModel(it) }
    }
}
