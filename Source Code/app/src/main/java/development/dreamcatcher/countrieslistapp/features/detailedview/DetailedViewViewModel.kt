package development.dreamcatcher.countrieslistapp.features.detailedview

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.data.repositories.CountriesRepository
import javax.inject.Inject

class DetailedViewViewModel @Inject constructor(private val countriesRepository: CountriesRepository)
    : ViewModel(), LifecycleObserver {

    fun getSingleSavedCountryByName(countryName: String): LiveData<CountryDatabaseEntity>? {
        return countriesRepository.getSingleSavedCountryByName(countryName)
    }
}