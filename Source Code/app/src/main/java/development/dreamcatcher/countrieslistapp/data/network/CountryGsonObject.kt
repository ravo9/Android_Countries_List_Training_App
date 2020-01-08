package development.dreamcatcher.countrieslistapp.data.network

import com.google.gson.annotations.SerializedName

// ApiResponse object and its sub-objects used for deserializing data coming from API endpoint
data class CountryGsonObject(
    @SerializedName("name")
    val name: String?,

    @SerializedName("capital")
    val capital: String?,

    @SerializedName("population")
    val population: Int?,

    @SerializedName("latlng")
    val latlng: List<Float>?
)