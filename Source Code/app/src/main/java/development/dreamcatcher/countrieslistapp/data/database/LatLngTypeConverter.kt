package development.dreamcatcher.countrieslistapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class LatLngTypeConverter {

    @TypeConverter
    fun pairToJson(value: List<Float>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToPair(value: String): List<Float>? {
        val objects = Gson().fromJson(value, Array<Float>::class.java) as Array<Float>
        val list = objects.toList()
        return list
    }
}