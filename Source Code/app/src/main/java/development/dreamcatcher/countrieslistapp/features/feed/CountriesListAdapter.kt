package development.dreamcatcher.countrieslistapp.features.feed

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import development.dreamcatcher.countrieslistapp.R
import development.dreamcatcher.countrieslistapp.data.database.CountryDatabaseEntity
import kotlinx.android.synthetic.main.main_feed_list_item.view.*

// Main adapter used for managing items list within the main RecyclerView (main feed listed)
class CountriesListAdapter (val activity: Activity, val clickListener: (String) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private var countriesList: List<CountryDatabaseEntity> = ArrayList()

    fun setCountries(countriesList: List<CountryDatabaseEntity>) {
        this.countriesList = countriesList
        notifyDataSetChanged()
    }

    fun changeSortingOrder() {
        this.countriesList = countriesList.reversed()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.main_feed_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Prepare fetched data
        val name = countriesList[position].name
        val flag = countriesList[position].flag
        val capital = countriesList[position].capital
        val population = countriesList[position].population

        // Set data within the holder
        holder.name.text = name
        holder.capital.text = (holder.itemView.context).getString(R.string.capital, capital)
        holder.population.text = (holder.itemView.context).getString(R.string.population, population)

        // Load thumbnail
        if (flag != null) {
            /*Glide.with(returnActivity())
                .load(getMyImageGrabberInstance())
                .into(imageView);*/
            //GlideToVectorYou.justLoadImage(activity, Uri.parse(flag), holder.thumbnail)
        }

        // Set onClickListener
        holder.rowContainer.setOnClickListener{
            val countryName = countriesList[position].name
            clickListener(countryName)
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val name = view.textView_name
    val capital = view.textView_capital
    val population = view.textView_population
    val latlng = view.textView_latlng
    val thumbnail = view.imageView_thumbnail
    val rowContainer = view.row_container
}