package co.develhope.meteoapp

import co.develhope.meteoapp.networking.GeocodingRetrofitIstance
import co.develhope.meteoapp.networking.OpenMeteoRetrofitInstance
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.networking.domainmodel.Place


class Data {
    suspend fun getWeeklyWeather(latitude: Double?, longitude: Double?): List<HomeCardInfo>? {
        return OpenMeteoRetrofitInstance().openMeteoApi.getWeeklyData(
            latitude = latitude,
            longitude = longitude,
        )?.daily?.toDomain()
    }

    suspend fun getDailyWeather(latitude: Double?, longitude: Double?): List<ForecastData>? {
        return OpenMeteoRetrofitInstance().openMeteoApi.getDailyData(
            latitude = latitude,
            longitude = longitude,
        )?.hourly?.toDomain()
    }

    suspend fun getSearchData(name : String?) : List<Place?> {
        return GeocodingRetrofitIstance().geoMeteoApi.getSearchData(
            name = name
        )?.results?.map { result ->
            result?.toDomain()
        } ?: emptyList()
    }

}
