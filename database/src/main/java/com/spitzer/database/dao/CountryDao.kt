package com.spitzer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.spitzer.database.model.CountryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Simulates the remote operations
 */
@Dao
interface CountryDao {

    @Transaction
    @Query(
        value = """
            SELECT * FROM countries ORDER BY 
            CASE WHEN :orderByPublishDate = 1 THEN publish_date END DESC, 
            CASE WHEN :orderByPublishDate = 0 THEN update_date END DESC
    """,
    )
    fun getCountries(
        orderByPublishDate: Boolean = true
    ): Flow<List<CountryEntity>>

    @Transaction
    @Upsert
    suspend fun upsertCountry(
        country: CountryEntity
    )

    @Transaction
    @Upsert
    suspend fun upsertCountries(
        countries: List<CountryEntity>
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCountries(
        countries: List<CountryEntity>
    )

    @Transaction
    @Query(
        value = """
            DELETE FROM countries
            WHERE cca3 in (:cca3Codes)
        """,
    )
    suspend fun deleteCountries(cca3Codes: List<String>)

    @Transaction
    @Query(
        value = """
            DELETE FROM countries
        """,
    )
    suspend fun deleteAllCountries()

    @Transaction
    suspend fun deleteAllAndInsert(countries: List<CountryEntity>) {
        deleteAllCountries()
        upsertCountries(countries)
    }
}
