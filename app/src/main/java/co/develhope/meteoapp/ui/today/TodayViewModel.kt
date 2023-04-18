package co.develhope.meteoapp.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.sharedPref.PrefsInterface
import kotlinx.coroutines.launch


class TodayViewModel(val prefs:PrefsInterface) : ViewModel() {

    private var _todayLiveData = MutableLiveData<TodayState>()
    val result: LiveData<TodayState>
        get() = _todayLiveData


    fun retrieveReposToday() {
        viewModelScope.launch {

            try {
                if (prefs.getMyCityObject() != null) {
                    val result = Data().getDailyWeather(
                        prefs.getMyCityObject()?.latitude,
                        prefs.getMyCityObject()?.longitude
                    )
                    _todayLiveData.value = result.let { it?.let { it1 -> TodayState.Success(it1) } }
                } else {
                    _todayLiveData.value = TodayState.Message
                }
            } catch (e: Exception) {
                _todayLiveData.value = e.message?.let { TodayState.Error(e) }
            }
        }

    }

    fun citySharedPrefToday() : String? {
        return prefs.getMyCityObject()?.city
    }

    fun regionSharedPrefToday() : String? {
        return prefs.getMyCityObject()?.region
    }
}
