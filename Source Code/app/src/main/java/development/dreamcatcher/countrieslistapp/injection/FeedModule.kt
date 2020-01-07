package development.dreamcatcher.countrieslistapp.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import development.dreamcatcher.countrieslistapp.data.database.CountriesDatabase
import development.dreamcatcher.countrieslistapp.data.database.CountriesDatabaseInteractor
import development.dreamcatcher.countrieslistapp.data.network.ApiClient
import development.dreamcatcher.countrieslistapp.data.network.CountriesNetworkInteractor
import development.dreamcatcher.countrieslistapp.data.network.NetworkAdapter
import development.dreamcatcher.countrieslistapp.data.repositories.CountriesRepository
import javax.inject.Singleton

@Module
class FeedModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Context): CountriesDatabase {
        return Room.databaseBuilder(application, CountriesDatabase::class.java, "main_database").build()
    }

    @Provides
    @Singleton
    fun providesCountriesDatabaseInteractor(countriesDatabase: CountriesDatabase): CountriesDatabaseInteractor {
        return CountriesDatabaseInteractor(countriesDatabase)
    }

    @Provides
    @Singleton
    fun providesCountriesNetworkInteractor(apiClient: ApiClient): CountriesNetworkInteractor {
        return CountriesNetworkInteractor(apiClient)
    }

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return NetworkAdapter.apiClient()
    }

    @Provides
    @Singleton
    fun providesCountriesRepository(countriesNetworkInteractor: CountriesNetworkInteractor,
                                   countriesDatabaseInteractor: CountriesDatabaseInteractor): CountriesRepository {
        return CountriesRepository(countriesNetworkInteractor, countriesDatabaseInteractor)
    }
}