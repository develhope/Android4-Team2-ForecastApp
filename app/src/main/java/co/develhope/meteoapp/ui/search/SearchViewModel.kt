package co.develhope.meteoapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.Place
import co.develhope.meteoapp.sharedPref.PrefsInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(val prefs: PrefsInterface,val data: Data) : ViewModel() {
    private var _searchStateLiveData = MutableLiveData<SearchState>()
    val searchStateLiveData: LiveData<SearchState>
        get() = _searchStateLiveData

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
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _searchStateLiveData.value = SearchState.Success(
                    data.getSearchData(city)
                )
            } catch (e: Exception) {
                _searchStateLiveData.value = SearchState.Error(e)
            }
        }
    }

    fun settingsMicrophone() : Boolean{
        return prefs.isGaranted
    }
}