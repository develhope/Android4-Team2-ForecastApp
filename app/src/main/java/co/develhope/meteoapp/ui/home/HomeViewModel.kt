package co.develhope.meteoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.ui.today.TodayState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var _homeStateLiveData = MutableLiveData<HomeState>()
    val homeStateLiveData: LiveData<HomeState>
        get() = _homeStateLiveData

    fun retrieveForecastInfo() {
        viewModelScope.launch {
            try {
                if (Data.citySearched != null) {
                    val result = Data.getWeeklyWeather(
                        Data.citySearched?.latitude,
                        Data.citySearched?.longitude
                    )
                    _homeStateLiveData.value = result?.let { HomeState.Success(it) }
                } else {
                    _homeStateLiveData.value = HomeState.FirstOpenFromUser
                }
            } catch (e: Exception) {
                _homeStateLiveData.value = e.message?.let { HomeState.Error(e) }
            }

        }
    }
}
