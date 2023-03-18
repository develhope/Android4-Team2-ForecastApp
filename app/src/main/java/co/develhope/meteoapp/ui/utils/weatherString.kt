package co.develhope.meteoapp.ui.utils

import co.develhope.meteoapp.R
import co.develhope.meteoapp.networking.domainmodel.Weather

fun Weather.weatherString(): String{
    return when (this) {
        Weather.SUNNY -> "Soleggiato"
        Weather.FOGGY -> "Nebbia"
        Weather.RAINY -> "Pioggia"
        Weather.HEAVYRAIN -> "Temporale"
        Weather.WINDY -> "Ventoso"
        Weather.CLOUDY -> "Nuvoloso"
        Weather.NIGHT -> "Nuvoloso"
        Weather.UNKNOWN -> "Soleggiato" // Da sostituire con un icona che indica che non c'è il dato
    }

}