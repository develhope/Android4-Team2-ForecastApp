package co.develhope.meteoapp.ui.tomorrow

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentDomaniBinding
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime


class TomorrowFragment : Fragment() {

    private var _binding: FragmentDomaniBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDomaniBinding.inflate(inflater, container, false)

        return binding.root
    }

   private fun createTomorrowScreenItems(dailyWeather: List<ForecastData>): List<TomorrowScreenData>{
        val listTomorrow = ArrayList<TomorrowScreenData>()
        listTomorrow.add(TomorrowScreenData.TSTitle(TomorrowTitle("Roma, ", "Lazio", OffsetDateTime.now())))

       val tomorrowWeather = dailyWeather.filter { it.date.dayOfYear == OffsetDateTime.now().dayOfYear }
       if (tomorrowWeather.isNotEmpty()) {
           tomorrowWeather.forEach {
               listTomorrow.add(TomorrowScreenData.TSForecast(it))
           }
       }

       return listTomorrow
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = activity?.window
        if (window != null) {
            window.statusBarColor = context?.getColor(R.color.background_screen) ?: 0
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvTomorrowScreen.layoutManager = layoutManager
        binding.rvTomorrowScreen.adapter = AdapterTomorrowScreen(emptyList())

        retrieveTomorrowForecastInfo()
    }

    private fun retrieveTomorrowForecastInfo(){
        lifecycleScope.launch {
            try {
                val dailyWeather: List<ForecastData> =
                    Data.getDailyWeather(41.9, 12.48) ?: emptyList()

                val listToShow = createTomorrowScreenItems(dailyWeather)
                binding.rvTomorrowScreen.adapter = AdapterTomorrowScreen(listToShow)

            } catch (e: Exception){
                Toast.makeText(requireContext(),"ERROR", Toast.LENGTH_LONG).show()
                Log.d("TomorrowFragment", "ERROR IN FRAGMENT : ${e.message}, ${e.cause}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}