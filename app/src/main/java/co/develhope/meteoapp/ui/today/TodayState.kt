package co.develhope.meteoapp.ui.today

import co.develhope.meteoapp.networking.domainmodel.ForecastData

sealed class TodayState() {
    data class Success(val repos: List<ForecastData>) : TodayState()
    data class Error(val e:Exception) : TodayState()
    object Message : TodayState()
}