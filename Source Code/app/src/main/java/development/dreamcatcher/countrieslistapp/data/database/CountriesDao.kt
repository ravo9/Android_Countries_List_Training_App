package development.dreamcatcher.countrieslistapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCountry(countryDatabaseEntity: CountryDatabaseEntity)

    @Query("SELECT * FROM countries WHERE name = :name LIMIT 1")
    fun getSingleSavedCountryByName(name: String): LiveData<CountryDatabaseEntity>?

    @Query("SELECT * FROM countries")
    fun getAllSavedCountries(): LiveData<List<CountryDatabaseEntity>>?

    @Query("DELETE FROM countries")
    fun clearDatabase()
}