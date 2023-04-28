package co.develhope.meteoapp.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.sharedPref.PrefsInterface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class TodayViewModel(val prefs:PrefsInterface,val data: Data) : ViewModel() {

    var todayLiveData = MutableSharedFlow<TodayState>()

    fun retrieveReposToday() {
        viewModelScope.launch {
            try {
                if (prefs.getMyCityObject() != null) {
                    val result = data.getDailyWeather(
                        prefs.getMyCityObject()?.latitude,
                        prefs.getMyCityObject()?.longitude
                    )
                    result.let { it?.let { it1 -> TodayState.Success(it1) } }
                        ?.let { todayLiveData.emit(it) }
                } else {
                    todayLiveData.emit(TodayState.Message)
                }
            } catch (e: Exception) {
                e.message?.let { TodayState.Error(e) }?.let { todayLiveData.emit(it) }
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
