package co.develhope.meteoapp.ui.tomorrow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class TomorrowState {
    data class Success(val list: List<ForecastData>) : TomorrowState()
    data class Error(val e:Exception) : TomorrowState()
    object Message : TomorrowState()
}

class TomorrowViewModel : ViewModel() {

    private var _tomorrowEventLiveData = MutableLiveData<TomorrowState>()
    val tomorrowEventLiveData: LiveData<TomorrowState>
        get() = _tomorrowEventLiveData

    fun retrieveReposTomorrow() {
        if(Data.citySearched != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val result = Data.getDailyWeather(
                    Data.citySearched?.latitude,
                    Data.citySearched?.longitude
                )
                try {
                    _tomorrowEventLiveData.value = TomorrowState.Success(result)
                } catch (e: Exception) {
                    _tomorrowEventLiveData.value = TomorrowState.Error(e)
                }
            }
        }else{
            _tomorrowEventLiveData.value = TomorrowState.Message
        }
    }
}