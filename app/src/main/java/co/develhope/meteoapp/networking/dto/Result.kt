package co.develhope.meteoapp.networking.dto

import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.Place
import co.develhope.meteoapp.ui.utils.weatherString

data class Result(
    val admin1: String?,
    val admin1_id: Int?,
    val admin2: String?,
    val admin2_id: Int?,
    val admin3: String?,
    val admin3_id: Int?,
    val country: String?,
    val country_code: String?,
    val country_id: Int?,
    val elevation: Double?,
    val feature_code: String?,
    val id: Int?,
    val latitude: Double?,
    val longitude: Double?,
    val name: String?,
    val population: Int?,
    val postcodes: List<String?>,
    val timezone: String?
){
    fun toDomain() : Place {
        return Place(
                city = name ?: "-",
                latitude = latitude?: 0.0,
                longitude = longitude?:0.0,
                region = admin1,
                temperature = Data.homeData?.maxTemp?: 0,
                weather = Data.homeData?.weather?.weatherString()?: ""
            )
        }

}
