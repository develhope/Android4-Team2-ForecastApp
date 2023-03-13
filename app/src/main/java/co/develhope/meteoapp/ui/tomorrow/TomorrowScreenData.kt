package co.develhope.meteoapp.ui.tomorrow

sealed class TomorrowScreenData() {
        data class TSTitle(val titleTomorrow: TomorrowTitle) : TomorrowScreenData()
        data class TSForecast(val forecast_data: ForecastData) : TomorrowScreenData()
    }