package co.develhope.meteoapp.ui.tomorrow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.ui.today.TodayState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class TomorrowState {
    data class Success(val list: List<ForecastData>) : TomorrowState()
    data class Error(val code: Int, val message: String) : TomorrowState()
    object Message : TomorrowState()
}

class TomorrowViewModel : ViewModel() {

    private var _tomorrowEventLiveData = MutableLiveData<TomorrowState>()
    val tomorrowEventLiveData: LiveData<TomorrowState>
        get() = _tomorrowEventLiveData

    fun retrieveReposTomorrow() {
        viewModelScope.launch {
            try {
                if (Data.citySearched != null) {
                    val result = Data.getDailyWeather(
                        Data.citySearched?.latitude,
                        Data.citySearched?.longitude
                    )
                    _tomorrowEventLiveData.value = result.let { it?.let { it1 -> TomorrowState.Success(it1) } }
                } else {
                    _tomorrowEventLiveData.value = TomorrowState.Message
                }
            } catch (e: Exception) {
                _tomorrowEventLiveData.value = e.message?.let { TomorrowState.Error(500, it) }
            }

        }
    }
}