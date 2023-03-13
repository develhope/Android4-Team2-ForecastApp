package co.develhope.meteoapp.networking.dto

data class CurrentWeather(
    val temperature: Double,
    val time: String,
    val weathercode: Int,
    val winddirection: Double,
    val windspeed: Double
)