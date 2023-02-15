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
        val wind : Int
    )
    data class HomeTitle(
        val city:String,
        val region:String
    )
    data class Home5NextDays(
        val next5Days:String
    )

    sealed class HomeScreenParts(){
        data class Title(val titleHome: HomeTitle):HomeScreenParts()
        data class Card(val cardInfo: HomeCardInfo):HomeScreenParts()
        data class Next5DaysString(val next5Days: Home5NextDays): HomeScreenParts()
    }

}