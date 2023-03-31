package co.develhope.meteoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.develhope.meteoapp.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var _homeStateLiveData = MutableLiveData<HomeState>()
    val homeStateLiveData: LiveData<HomeState>
        get() = _homeStateLiveData

    fun retrieveForecastInfo() {
        CoroutineScope(Dispatchers.Main).launch {
            if (Data.citySearched != null) {
                val result = Data.getWeeklyWeather(Data.citySearched?.latitude, Data.citySearched?.longitude)
                try {
                    _homeStateLiveData.value = HomeState.Success(result)
                } catch (e: Exception) {

                    _homeStateLiveData.value = HomeState.Error(e)
                }
            } else {
                _homeStateLiveData.value = HomeState.FirstOpenFromUser
            }
        }
    }
}
