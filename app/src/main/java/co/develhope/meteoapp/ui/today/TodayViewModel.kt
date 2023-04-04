package co.develhope.meteoapp.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TodayViewModel : ViewModel() {

    private var _todayLiveData = MutableLiveData<TodayState>()
    val result: LiveData<TodayState>
        get() = _todayLiveData


    fun retrieveReposToday() {
        viewModelScope.launch {

            try {
                if (Data.citySearched != null) {
                    val result = Data.getDailyWeather(
                        Data.citySearched?.latitude,
                        Data.citySearched?.longitude
                    )
                    _todayLiveData.value = result.let { it?.let { it1 -> TodayState.Success(it1) } }
                } else {
                    _todayLiveData.value = TodayState.Message
                }
            } catch (e: Exception) {
                _todayLiveData.value = e.message?.let { TodayState.Error(500, it) }
            }

        }

    }
}
