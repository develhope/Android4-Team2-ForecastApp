package co.develhope.meteoapp.ui.today

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentTodayBinding
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import co.develhope.meteoapp.ui.utils.firstAccess
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.threeten.bp.OffsetDateTime

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodayViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTodayBinding.inflate(inflater, container, false)

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
        binding.todayRecyclerView.layoutManager = layoutManager
        binding.todayRecyclerView.setHasFixedSize(true)
        binding.todayRecyclerView.adapter = TodayScreenAdapter(emptyList())
        observeTodayRepos()
        viewModel.retrieveReposToday()
    }

    private fun createTodayUI(listUI: List<ForecastData>) {
        val listToShow = createTodayScreenItems(listUI)
        binding.todayRecyclerView.adapter = TodayScreenAdapter(listToShow)
    }


    private fun observeTodayRepos() {
        lifecycleScope.launch {
            viewModel.todayLiveData.collect{
                when (it) {
                    is TodayState.Success -> createTodayUI(it.repos)
                    is TodayState.Error -> view?.let { it1 -> co.develhope.meteoapp.ui.utils.error(it1) }
                    is TodayState.Message -> context?.let { it1 ->
                        view?.let { it2 ->
                            firstAccess(
                                it2,
                                it1
                            )
                        }
                    }

                }
            }
        }
    }
    private fun createTodayScreenItems(dailyWeather: List<ForecastData>): List<TodayScreenData> {
        val listToday = ArrayList<TodayScreenData>()
        listToday.add(
            TodayScreenData.TodayScreenTitle(
                TodayTitleData(
                    viewModel.citySharedPrefToday(),
                    viewModel.regionSharedPrefToday(),
                    OffsetDateTime.now()
                )
            )
        )
        val todayWeather =
            dailyWeather.filter { it.date.dayOfYear == OffsetDateTime.now().dayOfYear && it.date.hour >= OffsetDateTime.now().hour }
        if (todayWeather.isNotEmpty()) {
            todayWeather.forEach {
                listToday.add(TodayScreenData.TodayScreenCard(it))
            }
        }
        return listToday
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}