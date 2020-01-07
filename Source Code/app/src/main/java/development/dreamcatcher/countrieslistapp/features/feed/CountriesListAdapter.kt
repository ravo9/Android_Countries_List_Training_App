package development.dreamcatcher.countrieslistapp.features.feed

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
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
        //val description = repositoriesList[position].description
        //val owner = repositoriesList[position].owner
        //val avatarUrl = repositoriesList[position].avatarUrl

        // Set data within the holder
        holder.name.text = name
        //holder.description.text = description
        //holder.owner.text = (holder.itemView.context).getString(R.string.owner, owner)

        // Load thumbnail
        if (flag != null) {
            /*Glide.with(returnActivity())
                .load(getMyImageGrabberInstance())
                .into(imageView);*/
            GlideToVectorYou.justLoadImage(activity, Uri.parse(flag), holder.thumbnail)
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
    //val description = view.textView_description
    //val owner = view.textView_owner
    val thumbnail = view.imageView_thumbnail
    val rowContainer = view.row_container
}