package co.develhope.meteoapp.networking.dto

import co.develhope.meteoapp.networking.domainmodel.Weather

fun Int.toWeather(): Weather {
    return when (this) {
        0 -> Weather.SUNNY
        1, 2, 3 -> Weather.CLOUDY
        45, 48 -> Weather.FOGGY
        51, 53, 55 -> Weather.RAINY
        56, 57 -> Weather.RAINY
        71, 73, 75 -> Weather.HEAVYRAIN
        80, 81, 82 -> Weather.HEAVYRAIN
        95 -> Weather.HEAVYRAIN
        96, 99 -> Weather.HEAVYRAIN
        else -> Weather.SUNNY
    }
}