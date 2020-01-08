package development.dreamcatcher.countrieslistapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.countrieslistapp.data.database.CountriesDao
import development.dreamcatcher.countrieslistapp.data.database.CountriesDatabase
import development.dreamcatcher.countrieslistapp.data.database.CountriesDatabaseInteractor
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.data.network.CountryGsonObject
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CountriesDatabaseInteractorTest {

    private var countriesDatabaseInteractor: CountriesDatabaseInteractor? = null
    private var fakeCountryGsonObject: CountryGsonObject? = null
    private var fakeCountryDatabaseEntity: CountryDatabaseEntity? = null

    @Mock
    private val countriesDatabase: CountriesDatabase? = null

    @Mock
    private val countriesDao: CountriesDao? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        countriesDatabaseInteractor = CountriesDatabaseInteractor(countriesDatabase!!)

        // Prepare fake data
        val name = "fake/country/name"
        val capital = "fake/country/capital"
        val population = 43234234

        // Prepare fake Gson (API) object
        fakeCountryGsonObject = CountryGsonObject(name, capital, population, null)

        // Prepare fake Country Entity (DB object)
        fakeCountryDatabaseEntity = CountryDatabaseEntity(name, capital, population, null)
    }

    @Test
    fun saveCountryByDatabaseInteractor() {

        // Perform the action
        val resultStatus = countriesDatabaseInteractor!!.addNewCountry(fakeCountryGsonObject).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchCountryByDatabaseInteractor() {

        // Prepare LiveData structure
        val countryEntityLiveData = MutableLiveData<CountryDatabaseEntity>()
        countryEntityLiveData.setValue(fakeCountryDatabaseEntity);

        // Set testing conditions
        Mockito.`when`(countriesDatabase?.getCountriesDao()).thenReturn(countriesDao)
        Mockito.`when`(countriesDao?.getSingleSavedCountryByName(anyString())).thenReturn(countryEntityLiveData)

        // Perform the action
        val storedCountry = countriesDatabaseInteractor?.getSingleSavedCountryByName("fake/country/name")

        // Check results
        Assert.assertSame(countryEntityLiveData, storedCountry);
    }
}