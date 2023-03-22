package co.develhope.meteoapp.ui.home

import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo

sealed class HomeEvents() {
    data class Success(val list: List<HomeCardInfo>) : HomeEvents()
    data class Error(val e: Exception) : HomeEvents()
    object FirstOpenFromUser : HomeEvents()
}
