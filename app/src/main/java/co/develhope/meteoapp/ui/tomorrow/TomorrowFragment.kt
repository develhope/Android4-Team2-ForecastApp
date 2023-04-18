package co.develhope.meteoapp.ui.tomorrow

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentTomorrowBinding
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.ui.utils.firstAccess
import org.koin.android.ext.android.inject
import org.threeten.bp.OffsetDateTime

class TomorrowFragment : Fragment() {

    private var _binding: FragmentTomorrowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TomorrowViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTomorrowBinding.inflate(inflater, container, false)

        return binding.root
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
                is TomorrowState.Error -> view?.let { it1 ->
                    co.develhope.meteoapp.ui.utils.error(
                        it1
                    )
                }
                is TomorrowState.Message -> context?.let { it1 ->
                    view?.let { it2 ->
                        firstAccess(
                            it2, it1
                        )
                    }
                }

            }
        }
    }
    private fun createTomorrowScreenItems(dailyWeather: List<ForecastData>): List<TomorrowScreenData> {
        val listTomorrow = ArrayList<TomorrowScreenData>()
        listTomorrow.add(
            TomorrowScreenData.TSTitle(
                TomorrowTitle(
                    viewModel.citySharedPrefTom(),
                    viewModel.regionSharedPrefTom(),
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}