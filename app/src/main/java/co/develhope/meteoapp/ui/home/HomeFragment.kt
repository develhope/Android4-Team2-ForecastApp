package co.develhope.meteoapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.Data.cardInfo1
import co.develhope.meteoapp.Data.cardInfo2
import co.develhope.meteoapp.Data.cardInfo3
import co.develhope.meteoapp.Data.cardInfo4
import co.develhope.meteoapp.Data.cardInfo5
import co.develhope.meteoapp.Data.cardInfo6
import co.develhope.meteoapp.databinding.FragmentHomeBinding
import co.develhope.meteoapp.networking.OpenMeteoRetrofitInstance
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var homeCardInfoList: List<HomeCardInfo> = emptyList()


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHomeFrag.layoutManager = layoutManager
        binding.recyclerViewHomeFrag.setHasFixedSize(true)
        binding.recyclerViewHomeFrag.adapter = HomeFragmentAdapter(listDataHomeScreen) {
            findNavController().navigate(it)
        }
        lifecycleScope.launch {

            try {
                val weeklyWeather = Data.getWeeklyWeather(37.7914, 15.2091)?.daily?.toDomain()
                val listDataHomeScreen = listOf<HomeScreenParts>(
                    HomeScreenParts.Title(title),
                    HomeScreenParts.Card(weeklyWeather?.getOrNull(0) ?: cardInfo1),
                    HomeScreenParts.Next5DaysString(nextDays),
                    HomeScreenParts.Card(weeklyWeather?.getOrNull(1) ?: cardInfo2),
                    HomeScreenParts.Card(weeklyWeather?.getOrNull(2) ?: cardInfo3),
                    HomeScreenParts.Card(weeklyWeather?.getOrNull(3) ?: cardInfo4),
                    HomeScreenParts.Card(weeklyWeather?.getOrNull(4) ?: cardInfo5),
                    HomeScreenParts.Card(weeklyWeather?.getOrNull(5) ?: cardInfo6)
                )
                binding.recyclerViewHomeFrag.adapter = HomeFragmentAdapter(listDataHomeScreen) {
                    findNavController().navigate(it)
                }

            } catch (e: Exception) {
                Log.d("HomeFragment", "ERROR IN FRAGMENT : ${e.message}, ${e.cause}")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val title = HomeTitle("Roma", "Lazio")
    private val nextDays = Home5NextDays("PROSSIMI 5 GIORNI")

    private var listDataHomeScreen = listOf<HomeScreenParts>(
        HomeScreenParts.Title(title),
        HomeScreenParts.Card(cardInfo1),
        HomeScreenParts.Next5DaysString(nextDays),
        HomeScreenParts.Card(cardInfo2),
        HomeScreenParts.Card(cardInfo3),
        HomeScreenParts.Card(cardInfo4),
        HomeScreenParts.Card(cardInfo5),
        HomeScreenParts.Card(cardInfo6)

    )


}