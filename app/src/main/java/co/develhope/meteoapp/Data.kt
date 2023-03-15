package co.develhope.meteoapp

import co.develhope.meteoapp.networking.OpenMeteoRetrofitInstance
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo


object Data {
    lateinit var nameCity : String

    suspend fun getWeeklyWeather(latitude: Double?, longitude: Double?): List<HomeCardInfo> {
        return OpenMeteoRetrofitInstance().openMeteoApi.getWeeklyData(
            latitude = latitude,
            longitude = longitude,
        ).daily.toDomain()
    }

    suspend fun getDailyWeather(latitude: Double, longitude: Double): List<ForecastData> {
        return OpenMeteoRetrofitInstance().openMeteoApi.getDailyData(
            latitude = latitude,
            longitude = longitude,
        ).hourly.toDomain()
    }
}
