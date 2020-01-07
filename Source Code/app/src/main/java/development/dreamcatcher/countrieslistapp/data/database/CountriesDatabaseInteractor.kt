package development.dreamcatcher.countrieslistapp.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.countrieslistapp.data.network.CountryGsonObject
import kotlinx.coroutines.launch

// Interactor used for communication between data country and internal database
class CountriesDatabaseInteractor(private val countriesDatabase: CountriesDatabase) {

    fun addNewCountry(country: CountryGsonObject?): LiveData<Boolean> {

        val saveCountryLiveData = MutableLiveData<Boolean>()

        country?.let {
            if (it.name != null) {
                val countryEntity = CountryDatabaseEntity(
                    name = it.name,
                    capital = it.capital,
                    population = it.population,
                    //latlng = it.latlng,
                    flag = it.flag)
                launch {
                    countriesDatabase.getCountriesDao().insertNewCountry(countryEntity)
                }
            }
        }
        saveCountryLiveData.postValue(true)
        return saveCountryLiveData
    }

    fun getSingleSavedCountryByName(name: String): LiveData<CountryDatabaseEntity>? {
        return countriesDatabase.getCountriesDao().getSingleSavedCountryByName(name)
    }

    fun getAllCountries(): LiveData<List<CountryDatabaseEntity>>? {
        return countriesDatabase.getCountriesDao().getAllSavedCountries()
    }

    fun clearDatabase() {
        launch {
            countriesDatabase.getCountriesDao().clearDatabase()
        }
    }
}



