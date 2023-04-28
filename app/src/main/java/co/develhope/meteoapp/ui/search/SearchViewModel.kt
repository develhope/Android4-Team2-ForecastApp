package co.develhope.meteoapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.Place
import co.develhope.meteoapp.sharedPref.PrefsInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SearchViewModel(val prefs: PrefsInterface,val data: Data) : ViewModel() {
    var searchStateLiveData = MutableSharedFlow<SearchState>()

    fun sendingCity(input: SearchEvents) =
        when (input) {
            is SearchEvents.CitySearched -> input.city?.let { retrieveSearchRepos(it) }
        }

    fun prefsSett(place: Place){
        prefs.saveMyCityObject(place)
        prefs.saveMyListCityObject(place)
    }

    fun getCityList() : MutableList<Place?>{
        return prefs.getMyListCityObject()
    }

    fun getCityObj(place: Place?){
        if (place != null) {
            prefs.saveMyCityObject(place)
        }
    }


    private fun retrieveSearchRepos(city: String?) {
        viewModelScope.launch {
            try {
                searchStateLiveData.emit(SearchState.Success(
                    data.getSearchData(city)
                ))
            } catch (e: Exception) {
                searchStateLiveData.emit(SearchState.Error(e))
            }
        }
    }

    fun settingsMicrophone() : Boolean{
        return prefs.isGaranted
    }
}