package co.develhope.meteoapp.ui.today

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentTodayBinding
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.ui.tomorrow.AdapterTomorrowScreen
import co.develhope.meteoapp.ui.tomorrow.TomorrowState
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TodayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTodayBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun createTodayScreenItems(dailyWeather: List<ForecastData>): List<TodayScreenData> {
        val listToday = ArrayList<TodayScreenData>()
        listToday.add(
            TodayScreenData.TodayScreenTitle(
                TodayTitleData(
                    Data.citySearched?.city,
                    Data.citySearched?.region,
                    OffsetDateTime.now()
                )
            )
        )
        val todayWeather =
            dailyWeather.filter { it.date.dayOfYear == OffsetDateTime.now().dayOfYear }
        if (todayWeather.isNotEmpty()) {
            todayWeather.forEach {
                listToday.add(TodayScreenData.TodayScreenCard(it))
            }
        }
        return listToday
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
        binding.todayRecyclerView.layoutManager = layoutManager
        binding.todayRecyclerView.setHasFixedSize(true)
        binding.todayRecyclerView.adapter = TodayScreenAdapter(emptyList())
        observeTodayRepos()
        viewModel.retrieveReposToday()
    }

    fun createTodayUI(listUI: List<ForecastData>){
        val listToShow = createTodayScreenItems(listUI)
        binding.todayRecyclerView.adapter = TodayScreenAdapter(listToShow)
    }


    fun observeTodayRepos(){
         viewModel.result.observe(viewLifecycleOwner){
            when(it) {
                is TodayState.Success -> createTodayUI(it.repos)
                is TodayState.Message -> errorMessage()
                is TodayState.Error -> findNavController().navigate(R.id.navigation_error)
            }
        }
    }


    private fun errorMessage(){
        findNavController().navigate(R.id.navigation_search)
        Toast.makeText(
            requireContext(),
            "Meteo non disponibile, seleziona una città",
            Toast.LENGTH_LONG
        ).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}