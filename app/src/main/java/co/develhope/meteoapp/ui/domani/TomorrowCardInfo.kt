package co.develhope.meteoapp.ui.domani

import co.develhope.meteoapp.Weather
import java.time.OffsetDateTime

data class TomorrowCardInfo(
    val date: OffsetDateTime,
    val weather: Weather,
    val temperature: Int,
    val precipitation: Int,
    val perc_temperature: Int,
    val UV_Index: Int,
    val humidity: Int,
    val wind: Int,
    val coverage: Int,
    val rain: Int
)