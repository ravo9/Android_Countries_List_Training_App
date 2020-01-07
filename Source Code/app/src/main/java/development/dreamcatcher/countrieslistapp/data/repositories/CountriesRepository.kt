package development.dreamcatcher.countrieslistapp.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import development.dreamcatcher.countrieslistapp.data.database.CountriesDatabaseInteractor
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.data.network.CountriesNetworkInteractor
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class CountriesRepository @Inject constructor(private val countriesNetworkInteractor:  CountriesNetworkInteractor,
                                              private val databaseInteractor: CountriesDatabaseInteractor) {

    fun getSingleSavedCountryByName(name: String): LiveData<CountryDatabaseEntity>? {
        return databaseInteractor.getSingleSavedCountryByName(name)
    }

    fun getAllCountries(backendUpdateRequired: Boolean): LiveData<List<CountryDatabaseEntity>>? {
        if (backendUpdateRequired) {
            updateDataFromBackEnd()
        }
        return databaseInteractor.getAllCountries()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return countriesNetworkInteractor.networkError
    }

    @SuppressLint("CheckResult")
    private fun updateDataFromBackEnd() {
        countriesNetworkInteractor.getAllCountries().subscribe {
            if (it.isSuccess && it.getOrDefault(null)?.size!! > 0) {

                // Clear database not to store outdated countries
                databaseInteractor.clearDatabase()

                // Save freshly fetched countries
                it.getOrNull()?.forEach {
                    databaseInteractor.addNewCountry(it)
                }
            }
        }
    }
}