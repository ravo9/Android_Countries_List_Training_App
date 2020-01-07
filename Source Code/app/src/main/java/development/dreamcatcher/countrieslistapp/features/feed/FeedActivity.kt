package development.dreamcatcher.countrieslistapp.features.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import development.dreamcatcher.countrieslistapp.R
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import development.dreamcatcher.countrieslistapp.features.detailedview.DetailedViewFragment
import development.dreamcatcher.countrieslistapp.injection.CountriesListApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.loading_badge.*
import javax.inject.Inject

// Main ('feed') view
class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel
    private lateinit var countriesListAdapter: CountriesListAdapter

    private val STATE_LOADING_ERROR = "STATE_LOADING_ERROR"
    private val STATE_CONTENT_LOADED = "STATE_CONTENT_LOADED"

    init {
        CountriesListApp.mainComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)

        // Initialize RecyclerView (Countries List)
        setupRecyclerView()

        // Fetch countries from the back-end and load them into the view
        subscribeForCountries()

        // Catch and handle potential network issues
        subscribeForNetworkError()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        main_feed_recyclerview.layoutManager = layoutManager
        countriesListAdapter = CountriesListAdapter(this, {
                countryName: String -> displayDetailedView(countryName)
        })
        main_feed_recyclerview.adapter = countriesListAdapter
    }

    private fun subscribeForCountries() {
        viewModel.getAllCountries()?.observe(this, Observer<List<CountryDatabaseEntity>> {

            if (!it.isNullOrEmpty()) {
                setViewState(STATE_CONTENT_LOADED)

                // Display fetched countries
                countriesListAdapter.setCountries(it)
            }
        })
    }

    private fun subscribeForNetworkError() {
        viewModel.getNetworkError()?.observe(this, Observer<Boolean> {

            // In case of Network Error...
            // If no countries have been cached
            if (countriesListAdapter.itemCount == 0) {
                setViewState(STATE_LOADING_ERROR)
            }

            // Display error message to the user
            Toast.makeText(this, R.string.network_problem_check_internet_connection,
                Toast.LENGTH_LONG) .show()
        })
    }

    private fun refreshCountriesSubscription() {
        viewModel.getAllCountries()?.removeObservers(this)
        subscribeForCountries()
    }

    private fun displayDetailedView(countryName: String) {

        val fragment = DetailedViewFragment()
        val bundle = Bundle()
        bundle.putString("countryName", countryName)
        fragment.arguments = bundle

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setViewState(state: String) {
        when(state) {
            STATE_LOADING_ERROR -> setupLoadingErrorView()
            STATE_CONTENT_LOADED -> setupContentLoadedView()
        }
    }

    private fun setupLoadingErrorView() {
        // Stop the loading progress bar (circle)
        progressBar.visibility = View.INVISIBLE

        // Display "Try Again" button
        tryagain_button.visibility = View.VISIBLE

        // Setup onClick listener that resets countries data subscription
        tryagain_button.setOnClickListener {
            refreshCountriesSubscription()

            // Re-display the loading progress bar (circle)
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun setupContentLoadedView() {
        // Hide the loading view
        loading_container.visibility = View.GONE
        appbar_container.visibility = View.VISIBLE

        // Setup refresh button
        btn_refresh.setOnClickListener{
            refreshCountriesSubscription()
        }
    }
}
