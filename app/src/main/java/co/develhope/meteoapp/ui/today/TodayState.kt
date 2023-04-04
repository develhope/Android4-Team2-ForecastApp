package co.develhope.meteoapp.ui.today

import co.develhope.meteoapp.networking.domainmodel.ForecastData

sealed class TodayState() {
    data class Success(val repos: List<ForecastData>) : TodayState()
    data class Error(val code: Int , val message :String) : TodayState()
    object Message : TodayState()
}