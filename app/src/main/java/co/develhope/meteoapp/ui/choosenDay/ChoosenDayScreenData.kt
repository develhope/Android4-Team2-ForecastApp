package co.develhope.meteoapp.ui.choosenDay

import co.develhope.meteoapp.networking.domainmodel.ForecastData

sealed class ChoosenDayScreenData() {
        data class TSTitle(val title_choosenDay: ChoosenDayTitle) : ChoosenDayScreenData()
        data class TSForecast(val forecast_data: ForecastData) : ChoosenDayScreenData()
    }