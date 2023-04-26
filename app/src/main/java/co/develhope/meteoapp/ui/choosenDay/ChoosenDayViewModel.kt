package co.develhope.meteoapp.ui.choosenDay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.sharedPref.PrefsInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

sealed class ChoosenDayState {
    data class Success(val list: List<ForecastData>) : ChoosenDayState()
    data class Error(val e:Exception) : ChoosenDayState()
    object Message : ChoosenDayState()
}

class ChoosenDayViewModel(val prefs: PrefsInterface,val data: Data) : ViewModel() {

     val choosenDayEventLiveData = MutableSharedFlow<ChoosenDayState>()


    fun getCityName() : String?{
        return prefs.getMyCityObject()?.city
    }
    fun getCityRegion(): String?{
        return prefs.getMyCityObject()?.region
    }
    fun getHomeDayOfYear() : Int? {
        return prefs.getMyHomeObject()?.dateTime?.toLocalDate()?.dayOfYear
    }

    fun retrieveReposChoosen() {
        viewModelScope.launch {
            if(prefs.getMyCityObject() != null) {
                viewModelScope.launch {
                    val result = data.getDailyWeather(
                        prefs.getMyCityObject()?.latitude,
                        prefs.getMyCityObject()?.longitude
                    )
                    try {
                        result?.let { ChoosenDayState.Success(it) }
                            ?.let { choosenDayEventLiveData.emit(it) }
                    } catch (e: Exception) {
                        choosenDayEventLiveData.emit(ChoosenDayState.Error(e))
                    }
                }
            }else{
                choosenDayEventLiveData.emit(ChoosenDayState.Message)
            }
        }

    }
}