package development.dreamcatcher.countrieslistapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryDatabaseEntity(
        @PrimaryKey val name: String,
        val capital: String?,
        val population: Int?,
        val latlng: List<Float>?)

