package development.dreamcatcher.countrieslistapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(CountryDatabaseEntity::class)], version = 1)
abstract class CountriesDatabase : RoomDatabase() {
    abstract fun getCountriesDao(): CountriesDao
}