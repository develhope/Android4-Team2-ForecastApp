package co.develhope.meteoapp.ui.search

sealed class SearchEvents {

    data class citySearched(val city : String) : SearchEvents()
}