package co.develhope.meteoapp

import java.time.OffsetDateTime

object Data {
    //HOME DATA
    data class HomeCardInfo(
        val dateTime: OffsetDateTime,
        val minTemp: Int,
        val maxTemp: Int,
        val weather: Weather,
        val rainFall: Int,
        val wind: Int
    )

    data class HomeTitle(
        val city: String,
        val region: String
    )

    data class Home5NextDays(
        val next5Days: String
    )

    sealed class HomeScreenParts() {
        data class Title(val titleHome: HomeTitle) : HomeScreenParts()
        data class Card(val cardInfo: HomeCardInfo) : HomeScreenParts()
        data class Next5DaysString(val next5Days: Home5NextDays) : HomeScreenParts()
    }

    fun weatherIcon(weather: Weather): Int {
        return when (weather) {
            Weather.SUNNY -> R.drawable.sun
            Weather.FOGGY -> R.drawable.suncloud
            Weather.RAINY -> R.drawable.rainsun
            Weather.HEAVYRAIN -> R.drawable.rainsun
            Weather.WINDY -> R.drawable.suncloud
            Weather.CLOUDY -> R.drawable.suncloud
        }
    }

    //CARD DATA FOR HOMESCREEN
    val cardInfo1 = Data.HomeCardInfo(
        OffsetDateTime.now(),
        15,
        25,
        Weather.SUNNY,
        0,
        10
    )
    val cardInfo2 = Data.HomeCardInfo(
        OffsetDateTime.now().plusDays(1),
        20,
        30,
        Weather.CLOUDY,
        40,
        20
    )
    val cardInfo3 = Data.HomeCardInfo(
        OffsetDateTime.now().plusDays(2),
        24,
        32,
        Weather.SUNNY,
        10,
        20
    )
    val cardInfo4 = Data.HomeCardInfo(
        OffsetDateTime.now().plusDays(3),
        10,
        15,
        Weather.HEAVYRAIN,
        90,
        30
    )
    val cardInfo5 = Data.HomeCardInfo(
        OffsetDateTime.now().plusDays(4),
        10,
        12,
        Weather.RAINY,
        70,
        10
    )
}