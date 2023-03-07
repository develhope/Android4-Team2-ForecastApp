package co.develhope.meteoapp

import android.util.Log
import co.develhope.meteoapp.networking.OpenMeteoRetrofitInstance
import co.develhope.meteoapp.networking.weeklySummary.WeatherResponse
import co.develhope.meteoapp.ui.home.ESwitchFragCard
import co.develhope.meteoapp.ui.home.HomeCardInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.OffsetDateTime
import retrofit2.HttpException

object Data {

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
    fun Int.toWeather() : Weather{
        return when(this){
            0 -> Weather.SUNNY
            1,2,3 -> Weather.CLOUDY
            45,48 -> Weather.FOGGY
            51,53,55 -> Weather.RAINY
            56,57 ->Weather.RAINY
            71,73,75 ->Weather.HEAVYRAIN
            80,81,82 -> Weather.HEAVYRAIN
            95 -> Weather.HEAVYRAIN
            96,99 ->Weather.HEAVYRAIN
            else -> Weather.CLOUDY
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
    val cardInfo6 = HomeCardInfo(
        OffsetDateTime.now().plusDays(5),
        0,
        22,
        Weather.RAINY,
        21,
        3,
        ESwitchFragCard.OGGI_FRAG
    )

    suspend fun getWeeklyWeather(latitude: Double, longitude: Double): WeatherResponse? {
        return try {
            withContext(Dispatchers.Main) {
                val response = OpenMeteoRetrofitInstance().openMeteoApi.getWeeklyData(
                    true,
                    listOf(
                        "weathercode",
                        "temperature_2m_max",
                        "temperature_2m_min",
                        "sunrise",
                        "sunset",
                        "precipitation_sum",
                        "rain_sum",
                        "windspeed_10m_max"),
                    latitude,
                    longitude,
                    "Europe/Berlin"
                )
                response
            }
        } catch (e: HttpException) {
            Log.d("HomeFragment", "Error : ${e.response()?.errorBody()?.toString()}")
            null
        }
    }
}
