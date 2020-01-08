package development.dreamcatcher.countrieslistapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.data.repositories.CountriesRepository
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val countriesRepository: CountriesRepository)
    : ViewModel(), LifecycleObserver {

    fun getAllCountries(backendUpdateRequired: Boolean): LiveData<List<CountryDatabaseEntity>>? {
        return countriesRepository.getAllCountries(backendUpdateRequired)
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return countriesRepository.getNetworkError()
    }
}