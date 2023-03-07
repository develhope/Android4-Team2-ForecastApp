package co.develhope.meteoapp.networking.weeklySummary

import retrofit2.Call
import retrofit2.Response
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
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("timezone") timezone: String? = "Europe/Berlin"
    ): WeatherResponse
}