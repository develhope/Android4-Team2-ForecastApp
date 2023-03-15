package co.develhope.meteoapp.networking.dto


import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.networking.domainmodel.ForecastData

import org.threeten.bp.OffsetDateTime

data class Hourly(
    val rain: List<Double>,
    val showers: List<Double>,
    val snowfall: List<Double>,
    val temperature_2m: List<Double>,
    val time: List<OffsetDateTime>,
    val weathercode: List<Int>,
    val windspeed_10m: List<Double>
){
    fun toDomain(): List<ForecastData> {
        return time.mapIndexed { index, hour ->
            ForecastData(
                date = hour,
                weather = weathercode.getOrNull(index)?.toWeather() ?: Weather.UNKNOWN,
                temperature = temperature_2m.getOrNull(index)?.toInt() ?: 0,
                precipitation = showers.getOrNull(index)?.toInt() ?: 0,
                perc_temperature = temperature_2m.getOrNull(index)?.toInt() ?: 0,
                UV_Index = 0,
                humidity = 0,
                wind = windspeed_10m.getOrNull(index)?.toInt() ?: 0,
                coverage = 0,
                rain = rain.getOrNull(index)?.toInt() ?: 0
            )
        }
    }
}