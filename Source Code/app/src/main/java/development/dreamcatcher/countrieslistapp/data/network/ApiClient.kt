package development.dreamcatcher.countrieslistapp.data.network

import io.reactivex.Observable
import retrofit2.http.GET

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET("/rest/v2/all")
    fun getCountries(): Observable<List<CountryGsonObject>>
}