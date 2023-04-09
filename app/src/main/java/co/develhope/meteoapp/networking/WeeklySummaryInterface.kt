package co.develhope.meteoapp.networking

import co.develhope.meteoapp.networking.dto.DaySummary
import co.develhope.meteoapp.networking.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeeklySummaryInterface {
    @GET("/v1/forecast")
    suspend fun getWeeklyData(
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("daily") daily: List<String> = listOf(
            "weathercode",
            "temperature_2m_max",
            "temperature_2m_min",
            "sunrise",
            "sunset",
            "precipitation_sum",
            "rain_sum",
            "windspeed_10m_max"
        ),
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?,
        @Query("timezone") timezone: String? = "Europe/Berlin"
    ): WeatherResponse?

    @GET("/v1/forecast")
    suspend fun getDailyData(
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("hourly") hourly: List<String> = listOf(
            "temperature_2m",
            "rain",
            "showers",
            "snowfall",
            "weathercode",
            "windspeed_10m"
        ),
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?,
        @Query("timezone") timezone: String? = "Europe/Berlin"
    ): DaySummary?
}