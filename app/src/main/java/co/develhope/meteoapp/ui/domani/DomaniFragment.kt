package co.develhope.meteoapp.ui.domani

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.Weather
import co.develhope.meteoapp.databinding.FragmentDomaniBinding
import java.time.OffsetDateTime

class DomaniFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvTomorrowScreen.layoutManager = layoutManager
        binding.rvTomorrowScreen.setHasFixedSize(true)
        binding.rvTomorrowScreen.adapter = AdapterTomorrowScreen(itemsTomorrowScreen)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val title_TomScreen = Data.TomorrowTitle("Milano, ", "Lombardia", OffsetDateTime.now())
    private val row_card_TomScreen = Data.ForecastData(OffsetDateTime.now(), Weather.SUNNY,
        22, 0, 21, 3, 20, 0, 0, 0)
    private val row_card_TomScreen2 = Data.ForecastData(OffsetDateTime.now().plusHours(1), Weather.FOGGY,
        25, 5, 23, 6, 16, 2, 5, 1)
    private val row_card_TomScreen3 = Data.ForecastData(OffsetDateTime.now().plusHours(2), Weather.RAINY,
        21, 80, 24, 4, 18, 5, 3, 10)

    private val itemsTomorrowScreen = listOf<Data.TomorrowScreenData>(
        Data.TomorrowScreenData.TSTitle(title_TomScreen),
        Data.TomorrowScreenData.TSForecast(row_card_TomScreen),
        Data.TomorrowScreenData.TSForecast(row_card_TomScreen2),
        Data.TomorrowScreenData.TSForecast(row_card_TomScreen3)
    )
}