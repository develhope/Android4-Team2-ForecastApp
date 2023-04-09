package co.develhope.meteoapp.ui.home

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
import co.develhope.meteoapp.databinding.FragmentHomeBinding
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.ui.home.adapter.Home5NextDays
import co.develhope.meteoapp.ui.home.adapter.HomeFragmentAdapter
import co.develhope.meteoapp.ui.home.adapter.HomeScreenParts
import co.develhope.meteoapp.ui.home.adapter.HomeTitle
import co.develhope.meteoapp.ui.utils.updateWidget
import org.threeten.bp.OffsetDateTime

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()


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

    private fun createHomeScreenItems(weeklyWeather: List<HomeCardInfo>): List<HomeScreenParts> {
        val list = ArrayList<HomeScreenParts>()
        list.add(
            HomeScreenParts.Title(
                HomeTitle(
                    Data.citySearched?.city,
                    Data.citySearched?.region
                )
            )
        )
        list.add(HomeScreenParts.Card(weeklyWeather.first()))
        list.add(HomeScreenParts.Next5DaysString(Home5NextDays("PROSSIMI 5 GIORNI")))

        val cleanedList = weeklyWeather.drop(1)
        cleanedList.map {
            list.add(HomeScreenParts.Card(it))
        }
        return list
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = activity?.window
        if (window != null) {
            window.statusBarColor = context?.getColor(R.color.bg_Home) ?: 0

            window.decorView.setSystemUiVisibility(0)

        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHomeFrag.layoutManager = layoutManager
        binding.recyclerViewHomeFrag.adapter = HomeFragmentAdapter(emptyList()) {}
        observeViewModel()
        viewModel.retrieveForecastInfo()
    }

    fun observeViewModel() {
        viewModel.homeStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is HomeState.Success -> createUI(it.list)
                is HomeState.FirstOpenFromUser -> firstAccess()
                is HomeState.Error -> findNavController().navigate(R.id.navigation_error)
            }
        }
    }

    private fun firstAccess() {
        findNavController().navigate(R.id.navigation_search)
        Toast.makeText(
            requireContext(),
            "Meteo non disponibile,seleziona una città!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun createUI(cardList: List<HomeCardInfo>) {
        val listToShow = createHomeScreenItems(cardList)
        binding.recyclerViewHomeFrag.adapter = HomeFragmentAdapter(listToShow) {
            Data.homeData = it
            val choosenFragment: Int =
                when {
                    it.dateTime.toLocalDate().isEqual(
                        OffsetDateTime.now().toLocalDate()
                    ) -> R.id.navigation_oggi
                    it.dateTime.toLocalDate().isEqual(
                        OffsetDateTime.now().plusDays(1).toLocalDate()
                    ) -> R.id.navigation_domani
                    else -> R.id.navigation_domani //gestirà il click sulle altre card
                }
            findNavController().navigate(choosenFragment)
            updateWidget(
                requireContext(), Data.citySearched?.city, Data.citySearched?.region,
                Data.homeData?.weather, Data.homeData?.maxTemp
            )

        }
    }

    override fun onResume() {
        super.onResume()
        updateWidget(
            requireContext(), Data.citySearched?.city, Data.citySearched?.region,
            Data.homeData?.weather, Data.homeData?.maxTemp
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}