package com.spitzer.data.mapper

import com.spitzer.database.testing.TestCountryEntityProvider
import com.spitzer.database.testing.TestFakeRemoteCountryEntityProvider
import org.junit.Test
import kotlin.test.assertEquals

class CountryModelMapperTest {

    private val mapper = CountryModelMapper()

    @Test
    fun `mapping Remote to DataModel and then to Entity provides same models when reverse mapping`() {
        val remoteModel = TestFakeRemoteCountryEntityProvider.getFakeRemoteCountryEntity()
        val dataModel = mapper.mapFakeRemoteEntityToDataModel(remoteModel)
        val entityModel = mapper.mapDataModelToEntity(dataModel)
        val dataModelFromEntityModel = mapper.mapEntityToDataModel(entityModel)
        val remoteModelFromDataModel =
            mapper.mapDataModelToFakeRemoteEntity(dataModelFromEntityModel)

        assertEquals(expected = remoteModel, actual = remoteModelFromDataModel)
        assertEquals(expected = dataModel, actual = dataModelFromEntityModel)
    }

    @Test
    fun `mapping Entity to DataModel and then to RemoteModel provides same models when reverse mapping`() {

        val entityModel = TestCountryEntityProvider.getCountryEntity()
        val dataModel = mapper.mapEntityToDataModel(entityModel)
        val remoteModel = mapper.mapDataModelToFakeRemoteEntity(dataModel)
        val dataModelFromRemoteModel = mapper.mapFakeRemoteEntityToDataModel(remoteModel)
        val entityModelFromDataModel =
            mapper.mapDataModelToEntity(dataModelFromRemoteModel)

        assertEquals(expected = entityModel, actual = entityModelFromDataModel)
        assertEquals(expected = dataModel, actual = dataModelFromRemoteModel)
    }
}
