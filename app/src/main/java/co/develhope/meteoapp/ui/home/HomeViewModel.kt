package co.develhope.meteoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.prefs
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var _homeStateLiveData = MutableLiveData<HomeState>()
    val homeStateLiveData: LiveData<HomeState>
        get() = _homeStateLiveData

    fun getCityName() : String?{
        return prefs.getMyCityObject()?.city
    }
    fun getCityRegion(): String?{
        return prefs.getMyCityObject()?.region
    }
    fun getHomeWeather() : Weather? {
        return prefs.getMyHomeObject()?.weather
    }
    fun getHomeTemp() : Int? {
        return prefs.getMyHomeObject()?.maxTemp
    }

    fun savePrefHome(homeCardInfo: HomeCardInfo){
        prefs.saveMyHomeObject(homeCardInfo)
    }

    fun retrieveForecastInfo() {
        viewModelScope.launch {
            try {
                if (prefs.getMyCityObject() != null) {
                    val result = Data().getWeeklyWeather(
                        prefs.getMyCityObject()?.latitude,
                        prefs.getMyCityObject()?.longitude
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
