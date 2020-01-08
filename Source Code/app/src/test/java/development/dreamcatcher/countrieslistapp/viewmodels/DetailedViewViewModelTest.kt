package development.dreamcatcher.countrieslistapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.data.repositories.CountriesRepository
import development.dreamcatcher.countrieslistapp.features.detailedview.DetailedViewViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailedViewViewModelTest {

    private var viewModel: DetailedViewViewModel? = null
    private var fakeCountryDatabaseEntity: CountryDatabaseEntity? = null

    @Mock
    private val countriesRepository: CountriesRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the country
        viewModel = DetailedViewViewModel(countriesRepository!!)

        // Prepare fake data
        val name = "fake/country/name"
        val capital = "fake/country/capital"
        val population = 43234234
        
        // Prepare fake Country Entity (DB object)
        fakeCountryDatabaseEntity = CountryDatabaseEntity(name, capital, population, null)
    }

    @Test
    fun fetchCountryByFeedViewModel() {

        // Prepare LiveData structure
        val countryEntityLiveData = MutableLiveData<CountryDatabaseEntity>()
        countryEntityLiveData.setValue(fakeCountryDatabaseEntity);

        // Prepare fake country name
        val fakeCountryName = "fake/country/name"

        // Set testing conditions
        Mockito.`when`(countriesRepository?.getSingleSavedCountryByName(fakeCountryName)).thenReturn(countryEntityLiveData)

        // Perform the action
        val storedCountries = viewModel?.getSingleSavedCountryByName(fakeCountryName)

        // Check results
        Assert.assertSame(countryEntityLiveData, storedCountries);
    }
}