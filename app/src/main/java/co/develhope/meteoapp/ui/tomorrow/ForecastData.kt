package co.develhope.meteoapp.ui.tomorrow

import co.develhope.meteoapp.Weather
import org.threeten.bp.OffsetDateTime

data class ForecastData(
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

