package co.develhope.meteoapp.ui.tomorrow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.networking.domainmodel.TodayCardData
import co.develhope.meteoapp.ui.utils.weatherIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class TomorrowEvents() {
    data class Success(val list: List<ForecastData>) : TomorrowEvents()
    data class Error(val e:Exception) : TomorrowEvents()
}

class TomorrowViewModel : ViewModel() {

    private var _tomorrowEventLiveData = MutableLiveData<TomorrowEvents>()
    val tomorrowEventLiveData: LiveData<TomorrowEvents>
        get() = _tomorrowEventLiveData


    fun retrieveReposTomorrow() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                    _tomorrowEventLiveData.value = TomorrowEvents.Success(
                        Data.getDailyWeather(
                            Data.citySearched.latitude,
                            Data.citySearched.longitude
                        )
                    )
                } catch (e:Exception){
                        _tomorrowEventLiveData.value = TomorrowEvents.Error(e)

            }
        }
    }
}