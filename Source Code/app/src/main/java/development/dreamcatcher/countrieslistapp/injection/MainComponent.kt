package development.dreamcatcher.countrieslistapp.injection

import dagger.Component
import development.dreamcatcher.countrieslistapp.data.database.CountriesDatabaseInteractor
import development.dreamcatcher.countrieslistapp.data.network.CountriesNetworkInteractor
import development.dreamcatcher.countrieslistapp.features.detailedview.DetailedViewFragment
import development.dreamcatcher.countrieslistapp.features.detailedview.DetailedViewViewModel
import development.dreamcatcher.countrieslistapp.features.feed.FeedActivity
import development.dreamcatcher.countrieslistapp.features.feed.FeedViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, FeedModule::class, ViewModelModule::class))
interface MainComponent {
    fun inject(feedActivity: FeedActivity)
    fun inject(detailedViewFragment: DetailedViewFragment)
    fun inject(feedViewModel: FeedViewModel)
    fun inject(detailedViewViewModel: DetailedViewViewModel)
    fun inject(countriesNetworkInteractor: CountriesNetworkInteractor)
    fun inject(countriesDatabaseInteractor: CountriesDatabaseInteractor)
}