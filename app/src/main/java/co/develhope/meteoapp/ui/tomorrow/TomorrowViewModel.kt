package co.develhope.meteoapp.ui.tomorrow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.sharedPref.PrefsInterface
import kotlinx.coroutines.launch

sealed class TomorrowState {
    data class Success(val list: List<ForecastData>) : TomorrowState()
    data class Error(val e:Exception) : TomorrowState()
    object Message : TomorrowState()
}

class TomorrowViewModel(val prefs: PrefsInterface,val data: Data) : ViewModel() {

    private var _tomorrowEventLiveData = MutableLiveData<TomorrowState>()
    val tomorrowEventLiveData: LiveData<TomorrowState>
        get() = _tomorrowEventLiveData

    fun retrieveReposTomorrow() {
        viewModelScope.launch {
            try {
                if (prefs.getMyCityObject() != null) {
                    val result = data.getDailyWeather(
                        prefs.getMyCityObject()?.latitude,
                        prefs.getMyCityObject()?.longitude
                    )
                    _tomorrowEventLiveData.value = result.let { it?.let { it1 -> TomorrowState.Success(it1) } }
                } else {
                    _tomorrowEventLiveData.value = TomorrowState.Message
                }
            } catch (e: Exception) {
                _tomorrowEventLiveData.value = e.message?.let { TomorrowState.Error(e) }
            }
        }
    }

    fun citySharedPrefTom() : String? {
        return prefs.getMyCityObject()?.city
    }

    fun regionSharedPrefTom() : String? {
        return prefs.getMyCityObject()?.region
    }
}