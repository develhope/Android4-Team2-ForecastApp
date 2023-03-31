package co.develhope.meteoapp.ui.tomorrow

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentTomorrowBinding
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import org.threeten.bp.OffsetDateTime

class TomorrowFragment : Fragment() {

    private var _binding: FragmentTomorrowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TomorrowViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTomorrowBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun createTomorrowScreenItems(dailyWeather: List<ForecastData>): List<TomorrowScreenData> {
        val listTomorrow = ArrayList<TomorrowScreenData>()
        listTomorrow.add(
            TomorrowScreenData.TSTitle(
                TomorrowTitle(
                    Data.citySearched?.city,
                    Data.citySearched?.region,
                    OffsetDateTime.now().plusDays(1)
                )
            )
        )

        val tomorrowWeather =
            dailyWeather.filter {
                it.date.dayOfYear == OffsetDateTime.now().dayOfYear.plus(1)
            }

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

        observeTomorrowRepos()
        viewModel.retrieveReposTomorrow()
    }

    private fun createTomorrowUI(listUI: List<ForecastData>) {
        val tomListToShow = createTomorrowScreenItems(listUI)
        binding.rvTomorrowScreen.adapter = AdapterTomorrowScreen(tomListToShow)
    }

    private fun observeTomorrowRepos() {
        viewModel.tomorrowEventLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is TomorrowState.Success -> createTomorrowUI(it.list)
                is TomorrowState.Message -> errorMessage()
                is TomorrowState.Error -> findNavController().navigate(R.id.navigation_error)
            }
        }
    }

    private fun errorMessage() {
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