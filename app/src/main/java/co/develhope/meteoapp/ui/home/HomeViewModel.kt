package co.develhope.meteoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.develhope.meteoapp.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var _homeEventsLiveData = MutableLiveData<HomeEvents>()
    val homeEventsLiveData: LiveData<HomeEvents>
        get() = _homeEventsLiveData

    fun retrieveForecastInfo() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _homeEventsLiveData.value = HomeEvents.Success(
                    Data.getWeeklyWeather(
                        Data.citySearched?.latitude,
                        Data.citySearched?.longitude
                    )
                )
            } catch (e: Exception) {
                if (Data.citySearched != null) {
                    _homeEventsLiveData.value = HomeEvents.Error(e)
                } else {
                    _homeEventsLiveData.value = HomeEvents.FirstOpenFromUser
                }
            }
        }
    }
}