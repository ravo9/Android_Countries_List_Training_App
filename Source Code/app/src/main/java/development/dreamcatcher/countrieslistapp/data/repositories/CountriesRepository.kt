package development.dreamcatcher.countrieslistapp.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import development.dreamcatcher.countrieslistapp.data.database.CountriesDatabaseInteractor
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.data.network.CountriesNetworkInteractor
import development.dreamcatcher.countrieslistapp.data.network.CountryGsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        return countriesNetworkInteractor.getNetworkError()
    }

    fun setNetworkError(t: Throwable?) {
        countriesNetworkInteractor.setNetworkError(t)
    }

    @SuppressLint("CheckResult")
    private fun updateDataFromBackEnd() {

        countriesNetworkInteractor.getAllCountries().enqueue(object: Callback<List<CountryGsonObject>> {

            override fun onResponse(call: Call<List<CountryGsonObject>>?, response: Response<List<CountryGsonObject>>?) {

                // Clear database not to store outdated countries
                databaseInteractor.clearDatabase()

                // Save freshly fetched countries
                response?.body()?.forEach {
                    databaseInteractor.addNewCountry(it)
                }
            }

            override fun onFailure(call: Call<List<CountryGsonObject>>?, t: Throwable?) {
                setNetworkError(t)
            }
        })
    }
}