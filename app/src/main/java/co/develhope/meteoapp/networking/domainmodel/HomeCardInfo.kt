package co.develhope.meteoapp.networking.domainmodel

import org.threeten.bp.OffsetDateTime

data class HomeCardInfo(
        val dateTime: OffsetDateTime,
        val minTemp: Int,
        val maxTemp: Int,
        val weather: Weather,
        val rainFall: Int,
        val wind: Int)