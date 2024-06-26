package com.spitzer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.spitzer.database.model.FakeRemoteCountryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Simulates the remote operations
 */
@Dao
interface FakeRemoteCountryDao {

    @Transaction
    @Query(
        value = """
            SELECT * FROM fake_remote_countries ORDER BY 
            CASE WHEN :orderByPublishDate = 1 THEN publish_date END DESC, 
            CASE WHEN :orderByPublishDate = 0 THEN update_date END DESC
    """,
    )
    fun getCountries(
        orderByPublishDate: Boolean = true
    ): Flow<List<FakeRemoteCountryEntity>>

    @Transaction
    @Upsert
    suspend fun upsertCountry(
        country: FakeRemoteCountryEntity
    )

    @Transaction
    @Upsert
    suspend fun upsertCountries(
        countries: List<FakeRemoteCountryEntity>
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCountries(
        countries: List<FakeRemoteCountryEntity>
    )

    @Transaction
    @Query(
        value = """
            DELETE FROM fake_remote_countries
            WHERE cca3 in (:cca3Codes)
        """,
    )
    suspend fun deleteCountries(cca3Codes: List<String>)

    @Transaction
    @Query(
        value = """
            DELETE FROM fake_remote_countries
        """,
    )
    suspend fun deleteAllCountries()

    @Transaction
    suspend fun deleteAllAndInsert(countries: List<FakeRemoteCountryEntity>) {
        deleteAllCountries()
        upsertCountries(countries)
    }
}
