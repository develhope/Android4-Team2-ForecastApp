package co.develhope.meteoapp.ui.utils

import co.develhope.meteoapp.R
import co.develhope.meteoapp.networking.domainmodel.Weather

fun weatherIcon(weather: Weather): Int {
        return when (weather) {
            Weather.SUNNY -> R.drawable.sun
            Weather.FOGGY -> R.drawable.suncloud
            Weather.RAINY -> R.drawable.rainsun
            Weather.HEAVYRAIN -> R.drawable.rainsun
            Weather.WINDY -> R.drawable.suncloud
            Weather.CLOUDY -> R.drawable.suncloud
            Weather.NIGHT -> R.drawable.moon
            Weather.UNKNOWN -> R.drawable.rainsun // Da sostituire con un icona che indica che non c'è il dato
        }
    }