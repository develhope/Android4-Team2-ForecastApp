package co.develhope.meteoapp.ui.today

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.R
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.databinding.FragmentOggiBinding
import co.develhope.meteoapp.networking.domainmodel.TodayCardData
import org.threeten.bp.OffsetDateTime

class TodayFragment : Fragment() {

    private var _binding: FragmentOggiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOggiBinding.inflate(inflater, container, false)

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
           binding.todayRecyclerView.layoutManager= layoutManager
        binding.todayRecyclerView.setHasFixedSize(true)
        binding.todayRecyclerView.adapter= TodayScreenAdapter(itemsTodayAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
private val titleScreenObj= TodayTitleData("Palermo", "Sicilia", OffsetDateTime.now())
private val cardScreenObj1= TodayCardData(OffsetDateTime.now(), Weather.SUNNY,31,
    0,45,5,60,7,24,0)
private val cardScreenObj2= TodayCardData(OffsetDateTime.now().plusHours(1), Weather.SUNNY,
    29, 0,44,8,67,4,20,0)
private val cardScreenObj3= TodayCardData(OffsetDateTime.now().plusHours(2), Weather.SUNNY,
30, 0, 42,5,70,12,45,0)
private val cardScreenObj4= TodayCardData(OffsetDateTime.now().plusHours(3), Weather.RAINY,
32, 60, 40, 4, 80, 12, 70, 4)

private val itemsTodayAdapter = listOf(
    TodayScreenData.TodayScreenTitle(titleScreenObj),
    TodayScreenData.TodayScreenCard(cardScreenObj1),
    TodayScreenData.TodayScreenCard(cardScreenObj2),
    TodayScreenData.TodayScreenCard(cardScreenObj3),
    TodayScreenData.TodayScreenCard(cardScreenObj4),

)