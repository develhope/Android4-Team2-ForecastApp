package co.develhope.meteoapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.Place
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private var _searchStateLiveData = MutableLiveData<SearchState>()
    val searchStateLiveData: LiveData<SearchState>
        get() = _searchStateLiveData

    fun sendingCity(input: SearchEvents) =
        when (input) {
            is SearchEvents.CitySearched -> input.city?.let { retrieveSearchRepos(it) }
        }


    fun retrieveSearchRepos(city: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _searchStateLiveData.value = SearchState.Success(
                    Data.getSearchData(city)
                )
            } catch (e: Exception) {
                _searchStateLiveData.value = SearchState.Error(e)
            }
        }
    }

}