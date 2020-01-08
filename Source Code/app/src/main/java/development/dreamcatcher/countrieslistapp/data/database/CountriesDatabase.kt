package development.dreamcatcher.countrieslistapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [(CountryDatabaseEntity::class)], version = 1)
@TypeConverters(LatLngTypeConverter::class)
abstract class CountriesDatabase : RoomDatabase() {
    abstract fun getCountriesDao(): CountriesDao
}