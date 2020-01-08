package development.dreamcatcher.countrieslistapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import javax.inject.Inject

// Interactor used for communication between data country and external API
class CountriesNetworkInteractor @Inject constructor(var apiClient: ApiClient) {

    private val networkError: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllCountries(): Call<List<CountryGsonObject>> {
        return apiClient.getCountries()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return networkError
    }

    fun setNetworkError(t: Throwable?) {
        networkError.postValue(true)
        if (t != null) { Log.e("Network Error: ", t.message) }
    }
}