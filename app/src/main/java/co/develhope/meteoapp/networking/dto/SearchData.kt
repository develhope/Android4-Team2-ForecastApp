package co.develhope.meteoapp.networking.dto

import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.Place
import co.develhope.meteoapp.ui.utils.weatherString

data class SearchData(
    val generationtime_ms: Double,
    val results: List<Result>


){
    fun toDomain() : Place{
        return Place(
            city = results.get(0).name,
            latitude = results.get(0).latitude,
            longitude = results.get(0).longitude,
            region = results.get(0).admin1,
            temperature = Data.homeData?.maxTemp?: 0,
            weather = Data.homeData?.weather?.weatherString()?: ""
        ) }
}
