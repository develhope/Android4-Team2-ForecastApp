package co.develhope.meteoapp.networking.dto

import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.Place
import co.develhope.meteoapp.ui.utils.weatherString

data class SearchData(
    val generationtime_ms: Double?,
    val results: List<Result?>


)
