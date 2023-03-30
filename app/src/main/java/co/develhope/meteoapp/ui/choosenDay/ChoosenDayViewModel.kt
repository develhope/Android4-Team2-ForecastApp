package co.develhope.meteoapp.ui.choosenDay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.ui.tomorrow.TomorrowState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class ChoosenDayState {
    data class Success(val list: List<ForecastData>) : ChoosenDayState()
    data class Error(val e:Exception) : ChoosenDayState()
    object Message : ChoosenDayState()
}

class ChoosenDayViewModel : ViewModel() {

    private var _choosenDayEventLiveData = MutableLiveData<ChoosenDayState>()
    val choosenDayEventLiveData: LiveData<ChoosenDayState>
        get() = _choosenDayEventLiveData

    fun retrieveReposChoosen() {
        if(Data.citySearched != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val result = Data.getDailyWeather(
                    Data.citySearched?.latitude,
                    Data.citySearched?.longitude
                )
                try {
                    _choosenDayEventLiveData.value = ChoosenDayState.Success(result)
                } catch (e: Exception) {
                    _choosenDayEventLiveData.value = ChoosenDayState.Error(e)
                }
            }
        }else{
            _choosenDayEventLiveData.value = ChoosenDayState.Message
        }
    }
}