package co.develhope.meteoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Data.cardInfo1
import co.develhope.meteoapp.Data.cardInfo2
import co.develhope.meteoapp.Data.cardInfo3
import co.develhope.meteoapp.Data.cardInfo4
import co.develhope.meteoapp.Data.cardInfo5
import co.develhope.meteoapp.databinding.FragmentHomeBinding

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
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHomeFrag.layoutManager = layoutManager
        binding.recyclerViewHomeFrag.setHasFixedSize(true)
        binding.recyclerViewHomeFrag.adapter = HomeFragmentAdapter(listDataHomeScreen)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val title = HomeTitle("Catania", "Sicilia")
    private val nextDays = Home5NextDays("Prossimi 5 giorni")

    private val listDataHomeScreen = listOf<HomeScreenParts>(
        HomeScreenParts.Title(title),
        HomeScreenParts.Card(cardInfo1),
        HomeScreenParts.Next5DaysString(nextDays),
        HomeScreenParts.Card(cardInfo2),
        HomeScreenParts.Card(cardInfo3),
        HomeScreenParts.Card(cardInfo4),
        HomeScreenParts.Card(cardInfo5)
    )

}