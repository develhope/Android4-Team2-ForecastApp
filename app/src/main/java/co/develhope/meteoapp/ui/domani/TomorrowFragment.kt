package co.develhope.meteoapp.ui.domani

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.R
import co.develhope.meteoapp.Weather
import co.develhope.meteoapp.databinding.FragmentDomaniBinding
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
        binding.rvTomorrowScreen.setHasFixedSize(true)
        binding.rvTomorrowScreen.adapter = AdapterTomorrowScreen(itemsTomorrowScreen)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val title_TomScreen = TomorrowTitle("Milano, ", "Lombardia", OffsetDateTime.now())
    private val row_card_TomScreen = ForecastData(OffsetDateTime.now(), Weather.SUNNY,
        22, 0, 21, 3, 20, 0, 0, 0)
    private val row_card_TomScreen2 = ForecastData(OffsetDateTime.now().plusHours(1), Weather.FOGGY,
        25, 5, 23, 6, 16, 2, 5, 1)
    private val row_card_TomScreen3 = ForecastData(OffsetDateTime.now().plusHours(2), Weather.RAINY,
        21, 80, 24, 4, 18, 5, 3, 10)

    private val itemsTomorrowScreen = listOf(
        TomorrowScreenData.TSTitle(title_TomScreen),
        TomorrowScreenData.TSForecast(row_card_TomScreen),
        TomorrowScreenData.TSForecast(row_card_TomScreen2),
        TomorrowScreenData.TSForecast(row_card_TomScreen3)
    )
}