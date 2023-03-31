package co.develhope.meteoapp.ui.choosenDay

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
import co.develhope.meteoapp.databinding.FragmentChoosenDayBinding
import co.develhope.meteoapp.networking.domainmodel.ForecastData
import org.threeten.bp.OffsetDateTime

class ChoosenDayFragment : Fragment() {

    private var _binding: FragmentChoosenDayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChoosenDayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChoosenDayBinding.inflate(inflater , container , false)

        return binding.root
    }

    private fun createChoosenScreenItems(choosenDayWheater: List<ForecastData>): List<ChoosenDayScreenData> {
        val listChoosen = ArrayList<ChoosenDayScreenData>()
        listChoosen.add(
            ChoosenDayScreenData.TSTitle(
                ChoosenDayTitle(
                    Data.citySearched?.city, Data.citySearched?.region, OffsetDateTime.now()
                )
            )
        )

        val choosenWeather =
            choosenDayWheater.filter { it.date.dayOfYear == Data.homeData?.dateTime?.toLocalDate()?.dayOfYear }
        if (choosenWeather.isNotEmpty()) {
            choosenWeather.forEach {
                listChoosen.add(ChoosenDayScreenData.TSForecast(it))
            }
        }

        return listChoosen
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val window = activity?.window
        if (window != null) {
            window.statusBarColor = context?.getColor(R.color.background_screen) ?: 0
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        }

        val layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
        binding.rvChoosendayScreen.layoutManager = layoutManager
        binding.rvChoosendayScreen.adapter = AdapterChoosenDay(emptyList())

        observeChoosenRepos()
        viewModel.retrieveReposChoosen()
    }

    private fun createChoosenDayUI(listUI: List<ForecastData>){
        val chooseListToShow = createChoosenScreenItems(listUI)
        binding.rvChoosendayScreen.adapter = AdapterChoosenDay(chooseListToShow)
    }

    private fun observeChoosenRepos(){
        viewModel.choosenDayEventLiveData.observe(viewLifecycleOwner){
            when(it) {
                is ChoosenDayState.Success -> createChoosenDayUI(it.list)
                is ChoosenDayState.Message -> errorMessage()
                is ChoosenDayState.Error -> findNavController().navigate(R.id.navigation_error)
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