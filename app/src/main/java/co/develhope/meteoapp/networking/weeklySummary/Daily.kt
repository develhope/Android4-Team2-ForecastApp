package co.develhope.meteoapp.networking.weeklySummary

import co.develhope.meteoapp.Data.toWeather
import co.develhope.meteoapp.Weather
import co.develhope.meteoapp.ui.home.HomeCardInfo
import org.threeten.bp.OffsetDateTime

data class Daily(
    val precipitation_sum: List<Double>,
    val rain_sum: List<Double>,
    val sunrise: List<OffsetDateTime>,
    val sunset: List<OffsetDateTime>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<OffsetDateTime>,
    val weathercode: List<Int>,
    val windspeed_10m_max: List<Double>
) {
    fun toDomain(): List<HomeCardInfo> {
        return time.mapIndexed { index, day ->
            HomeCardInfo(
                dateTime = day,
                minTemp = temperature_2m_min.getOrNull(index)?.toInt() ?: 0,
                maxTemp = temperature_2m_max.getOrNull(index)?.toInt() ?: 0,
                rainFall = precipitation_sum.getOrNull(index)?.toInt() ?: 0,
                weather = weathercode.getOrNull(index)?.toWeather() ?: Weather.UNKNOWN,
                wind = windspeed_10m_max.getOrNull(index)?.toInt() ?: 0
            )
        }
    }
}