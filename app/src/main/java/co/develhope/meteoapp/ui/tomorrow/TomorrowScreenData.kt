package co.develhope.meteoapp.ui.tomorrow

import co.develhope.meteoapp.networking.domainmodel.ForecastData

sealed class TomorrowScreenData() {
        data class TSTitle(val titleTomorrow: TomorrowTitle) : TomorrowScreenData()
        data class TSForecast(val forecast_data: ForecastData) : TomorrowScreenData()
    }