package co.develhope.meteoapp.ui.search

sealed class SearchEvents {

    data class CitySearched(val city : String?) : SearchEvents()
}