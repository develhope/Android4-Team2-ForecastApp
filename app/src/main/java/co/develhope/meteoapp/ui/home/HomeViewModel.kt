package co.develhope.meteoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.sharedPref.PrefsInterface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(val prefs: PrefsInterface,val data: Data) : ViewModel() {
    val homeStateLiveData = MutableSharedFlow<HomeState>()

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
                    val result = data.getWeeklyWeather(
                        prefs.getMyCityObject()?.latitude,
                        prefs.getMyCityObject()?.longitude
                    )
                    result?.let { HomeState.Success(it) }?.let { homeStateLiveData.emit(it) }
                } else {
                    homeStateLiveData.emit( HomeState.FirstOpenFromUser)
                }
            } catch (e: Exception) {
                e.message?.let { HomeState.Error(e) }?.let { homeStateLiveData.emit(it) }
            }

        }
    }
}
