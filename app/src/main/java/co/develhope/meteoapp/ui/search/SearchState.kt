package co.develhope.meteoapp.ui.search

import co.develhope.meteoapp.networking.domainmodel.Place

sealed class SearchState() {

    data class Success(val list: List<Place?>) : SearchState()
    data class Error(val e: Exception) : SearchState()
}