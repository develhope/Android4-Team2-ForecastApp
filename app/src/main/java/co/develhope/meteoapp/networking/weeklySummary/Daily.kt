package co.develhope.meteoapp.networking.weeklySummary

import co.develhope.meteoapp.Data.toWeather
import co.develhope.meteoapp.Weather
import co.develhope.meteoapp.ui.home.ESwitchFragCard
import co.develhope.meteoapp.ui.home.HomeCardInfo
import co.develhope.meteoapp.ui.home.HomeFragment
import co.develhope.meteoapp.ui.home.HomeFragmentAdapter
import org.threeten.bp.OffsetDateTime

data class Daily(
    val precipitation_sum: List<Double>,
    val rain_sum: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>,
    val windspeed_10m_max : List<Double>
    ){
    fun toDomain() : List<HomeCardInfo>{
        return temperature_2m_max.indices.map {index ->
            HomeCardInfo(
                dateTime = OffsetDateTime.now().plusDays(index.toLong()),
                minTemp = temperature_2m_min.getOrNull(index)?.toInt() ?: 0,
                maxTemp = temperature_2m_max.getOrNull(index)?.toInt() ?: 0,
                rainFall = precipitation_sum.getOrNull(index)?.toInt() ?: 0,
                weather = (weathercode.getOrNull(index)?.toWeather() ?: 0) as Weather,
                wind = windspeed_10m_max.getOrNull(index)?.toInt() ?: 0,
                cardSwitch = if (OffsetDateTime.now().isEqual(OffsetDateTime.now())){
                    ESwitchFragCard.OGGI_FRAG
                } else{
                    ESwitchFragCard.DOMANI_FRAG
                }
            )
        }
    }
}