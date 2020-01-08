package development.dreamcatcher.countrieslistapp.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.countrieslistapp.data.database.CountriesDatabaseInteractor
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.data.network.CountriesNetworkInteractor
import development.dreamcatcher.countrieslistapp.data.repositories.CountriesRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CountriesRepositoryTest {

    private var countriesRepository: CountriesRepository? = null
    private var fakeCountryDatabaseEntity: CountryDatabaseEntity? = null
    private var fakeCountryEntitiesList = ArrayList<CountryDatabaseEntity>()

    @Mock
    private val countriesDatabaseInteractor: CountriesDatabaseInteractor? = null

    @Mock
    private val countriesNetworkInteractor: CountriesNetworkInteractor? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        countriesRepository = CountriesRepository(countriesNetworkInteractor!!, countriesDatabaseInteractor!!)

        // Prepare fake data
        val name = "fake/country/name"
        val capital = "fake/country/capital"
        val population = 43234234

        // Prepare fake Country Entity (DB object)
        fakeCountryDatabaseEntity = CountryDatabaseEntity(name, capital, population, null)

        // Prepare fake Countries Entities List
        fakeCountryEntitiesList.add(fakeCountryDatabaseEntity!!)
    }

    @Test
    fun fetchAllCountriesByCountriesRepository() {

        // Prepare LiveData structure
        val countryEntityLiveData = MutableLiveData<List<CountryDatabaseEntity>>()
        countryEntityLiveData.setValue(fakeCountryEntitiesList);

        // Set testing conditions
        Mockito.`when`(countriesDatabaseInteractor?.getAllCountries()).thenReturn(countryEntityLiveData)

        // Perform the action
        val storedCountries = countriesRepository?.getAllCountries(false)

        // Check results
        Assert.assertSame(countryEntityLiveData, storedCountries);
    }

    @Test
    fun fetchCountryByCountriesRepository() {

        // Prepare LiveData structure
        val countryEntityLiveData = MutableLiveData<CountryDatabaseEntity>()
        countryEntityLiveData.setValue(fakeCountryDatabaseEntity);

        // Prepare fake country name
        val fakeCountryName = "fake/country/name"

        // Set testing conditions
        Mockito.`when`(countriesDatabaseInteractor?.getSingleSavedCountryByName(fakeCountryName))
            .thenReturn(countryEntityLiveData)

        // Perform the action
        val storedCountry = countriesRepository?.getSingleSavedCountryByName(fakeCountryName)

        // Check results
        Assert.assertSame(countryEntityLiveData, storedCountry);
    }
}