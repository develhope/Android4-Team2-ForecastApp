package co.develhope.meteoapp

import co.develhope.meteoapp.ui.home.ESwitchFragCard
import co.develhope.meteoapp.ui.home.HomeCardInfo
import org.threeten.bp.OffsetDateTime
object Data{
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
    val cardInfo1 = HomeCardInfo(
        OffsetDateTime.now(),
        15,
        25,
        Weather.SUNNY,
        0,
        10,
        ESwitchFragCard.OGGI_FRAG
    )
    val cardInfo2 = HomeCardInfo(
        OffsetDateTime.now().plusDays(1),
        20,
        30,
        Weather.CLOUDY,
        40,
        20,
        ESwitchFragCard.DOMANI_FRAG
    )
    val cardInfo3 = HomeCardInfo(
        OffsetDateTime.now().plusDays(2),
        24,
        32,
        Weather.SUNNY,
        10,
        20,
        ESwitchFragCard.OGGI_FRAG
    )
    val cardInfo4 = HomeCardInfo(
        OffsetDateTime.now().plusDays(3),
        10,
        15,
        Weather.HEAVYRAIN,
        90,
        30,
        ESwitchFragCard.OGGI_FRAG
    )
    val cardInfo5 = HomeCardInfo(
        OffsetDateTime.now().plusDays(4),
        10,
        12,
        Weather.RAINY,
        70,
        10,
        ESwitchFragCard.OGGI_FRAG
    )
}