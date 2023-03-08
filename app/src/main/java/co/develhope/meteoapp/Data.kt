package co.develhope.meteoapp

import android.util.Log
import co.develhope.meteoapp.networking.OpenMeteoRetrofitInstance
import co.develhope.meteoapp.networking.weeklySummary.WeatherResponse
import co.develhope.meteoapp.ui.domani.ForecastData
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
            Weather.UNKNOWN -> R.drawable.suncloud // Da sostituire con un icona che indica che non c'è il dato
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

    suspend fun getWeeklyWeather(latitude: Double, longitude: Double): List<HomeCardInfo> {
        return OpenMeteoRetrofitInstance().openMeteoApi.getWeeklyData(
            latitude = latitude,
            longitude = longitude,
        ).daily.toDomain()
    }

    suspend fun getDailyWeather(latitude: Double, longitude: Double): List<ForecastData> {
        return OpenMeteoRetrofitInstance().openMeteoApi.getDailyData(
            latitude = latitude,
            longitude = longitude,
        ).hourly.toDomain()
    }
}
