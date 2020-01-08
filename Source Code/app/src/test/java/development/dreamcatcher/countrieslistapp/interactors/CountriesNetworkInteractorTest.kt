package development.dreamcatcher.countrieslistapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import development.dreamcatcher.countrieslistapp.data.network.ApiClient
import development.dreamcatcher.countrieslistapp.data.network.CountriesNetworkInteractor
import development.dreamcatcher.countrieslistapp.data.network.CountryGsonObject
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CountriesNetworkInteractorTest {

    private var countriesNetworkInteractor: CountriesNetworkInteractor? = null
    private var fakeCountryGsonObject: CountryGsonObject? = null

    @Mock
    private val apiClient: ApiClient? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        countriesNetworkInteractor = CountriesNetworkInteractor(apiClient!!)

        // Prepare fake data
        val name = "fake/country/name"
        val capital = "fake/country/capital"
        val population = 43234234

        // Prepare fake Gson (API) object
        fakeCountryGsonObject = CountryGsonObject(name, capital, population, null)
    }
}
