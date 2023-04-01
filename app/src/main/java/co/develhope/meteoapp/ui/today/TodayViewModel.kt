package co.develhope.meteoapp.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.develhope.meteoapp.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class TodayViewModel : ViewModel() {

    private var _todayLiveData = MutableLiveData<TodayState>()
    val result: LiveData<TodayState>
        get() = _todayLiveData


     fun retrieveReposToday() {
        if (Data.citySearched != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val result = Data.getDailyWeather(
                    Data.citySearched?.latitude,
                    Data.citySearched?.longitude
                )
                try {
                    _todayLiveData.value = TodayState.Success(result)
                } catch (e: Exception) {
                    _todayLiveData.value =
                        TodayState.Error(e)
                }
            }
        } else {_todayLiveData.value = TodayState.Message
        }
    }
}