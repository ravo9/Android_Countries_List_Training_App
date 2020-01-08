package development.dreamcatcher.countrieslistapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.data.repositories.CountriesRepository
import development.dreamcatcher.countrieslistapp.features.feed.FeedViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class FeedViewModelTest {

    private var viewModel: FeedViewModel? = null
    private var fakeCountryDatabaseEntity: CountryDatabaseEntity? = null
    private var fakeCountryEntitiesList = ArrayList<CountryDatabaseEntity>()

    @Mock
    private val countriesRepository: CountriesRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the country
        viewModel = FeedViewModel(countriesRepository!!)

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
    fun fetchAllCountriesByFeedViewModel() {

        // Prepare LiveData structure
        val countryEntityLiveData = MutableLiveData<List<CountryDatabaseEntity>>()
        countryEntityLiveData.setValue(fakeCountryEntitiesList)

        // Set testing conditions
        Mockito.`when`(countriesRepository?.getAllCountries(false)).thenReturn(countryEntityLiveData)

        // Perform the action
        val storedCountries = viewModel?.getAllCountries(false)

        // Check results
        Assert.assertSame(countryEntityLiveData, storedCountries);
    }
}