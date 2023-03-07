package co.develhope.meteoapp.ui.home

import co.develhope.meteoapp.Weather
import org.threeten.bp.OffsetDateTime

data class HomeCardInfo(
        val dateTime: OffsetDateTime,
        val minTemp: Int,
        val maxTemp: Int,
        val weather: Weather,
        val rainFall: Int,
        val wind: Int,
        val cardSwitch: ESwitchFragCard?
    )