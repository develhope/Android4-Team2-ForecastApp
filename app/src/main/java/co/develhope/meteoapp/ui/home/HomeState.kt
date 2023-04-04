package co.develhope.meteoapp.ui.home

import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo

sealed class HomeState() {
    data class Success(val list: List<HomeCardInfo>) : HomeState()
    data class Error(val code: Int , val message :String) : HomeState()
    object FirstOpenFromUser : HomeState()
}
