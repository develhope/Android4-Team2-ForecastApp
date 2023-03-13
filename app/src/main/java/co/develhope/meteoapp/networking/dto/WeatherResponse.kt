package co.develhope.meteoapp.networking.dto

import co.develhope.meteoapp.networking.dto.CurrentWeather
import co.develhope.meteoapp.networking.dto.Daily
import co.develhope.meteoapp.networking.dto.DailyUnits

data class WeatherResponse(
    val current_weather: CurrentWeather,
    val daily: Daily,
    val daily_units: DailyUnits,
    val elevation: Double,
    val generationtime_ms: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)