package co.develhope.meteoapp

import java.time.OffsetDateTime

object Data {
data class HomeCardInfo(
    val dateTime: OffsetDateTime,
    val minTemp : Int,
    val maxTemp: Int,
    val weather : Weather
)
    enum class Weather()
}