package co.develhope.meteoapp.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentHomeBinding
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.ui.home.adapter.Home5NextDays
import co.develhope.meteoapp.ui.home.adapter.HomeFragmentAdapter
import co.develhope.meteoapp.ui.home.adapter.HomeScreenParts
import co.develhope.meteoapp.ui.home.adapter.HomeTitle
import co.develhope.meteoapp.ui.utils.firstAccess
import co.develhope.meteoapp.ui.utils.updateWidget
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.threeten.bp.OffsetDateTime

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by inject()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = activity?.window
        if (window != null) {
            window.statusBarColor = context?.getColor(R.color.bg_Home)!!
            window.decorView.setSystemUiVisibility(0)

        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHomeFrag.layoutManager = layoutManager
        binding.recyclerViewHomeFrag.adapter = HomeFragmentAdapter(emptyList()) {}
        observeViewModel()
        viewModel.retrieveForecastInfo()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.homeStateLiveData.collect{
                when (it) {
                    is HomeState.Success -> createUI(it.list)
                    is HomeState.Error -> view?.let { it1 -> co.develhope.meteoapp.ui.utils.error(it1) }
                    is HomeState.FirstOpenFromUser -> view?.let { it1 ->
                        context?.let { it2 ->
                            firstAccess(
                                it1,
                                it2
                            )
                        }
                    }
                }
            }
        }
    }


    private fun createUI(cardList: List<HomeCardInfo>) {
        val listToShow = createHomeScreenItems(cardList)
        binding.recyclerViewHomeFrag.adapter = HomeFragmentAdapter(listToShow) {
            viewModel.savePrefHome(it)

            val choosenFragment: Int =
                when {
                    it.dateTime.toLocalDate().isEqual(
                        OffsetDateTime.now().toLocalDate()
                    ) -> R.id.navigation_oggi
                    it.dateTime.toLocalDate().isEqual(
                        OffsetDateTime.now().plusDays(1).toLocalDate()
                    ) -> R.id.navigation_domani
                    else -> R.id.navigation_choosenDay //gestirà il click sulle altre card
                }
            findNavController().navigate(choosenFragment)
            updateWidget(
                requireContext(), viewModel.getCityName(), viewModel.getCityRegion(),
                viewModel.getHomeWeather(), viewModel.getHomeTemp()
            )
        }
    }
    private fun createHomeScreenItems(weeklyWeather: List<HomeCardInfo>): List<HomeScreenParts> {
        val list = ArrayList<HomeScreenParts>()
        list.add(
            HomeScreenParts.Title(
                HomeTitle(
                    viewModel.getCityName(),
                    viewModel.getCityRegion()
                )
            )
        )
        list.add(HomeScreenParts.Card(weeklyWeather.first()))
        list.add(HomeScreenParts.Next5DaysString(Home5NextDays("PROSSIMI GIORNI")))

        val cleanedList = weeklyWeather.drop(1)
        cleanedList.map {
            list.add(HomeScreenParts.Card(it))
        }
        return list
    }

    override fun onResume() {
        super.onResume()
        updateWidget(
            requireContext(),  viewModel.getCityName(), viewModel.getCityRegion(),
            viewModel.getHomeWeather(), viewModel.getHomeTemp()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}