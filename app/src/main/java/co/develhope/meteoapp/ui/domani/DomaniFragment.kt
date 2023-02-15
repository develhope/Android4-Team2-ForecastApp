package co.develhope.meteoapp.ui.domani

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Weather
import co.develhope.meteoapp.databinding.FragmentDomaniBinding

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
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listItems = mutableListOf(
            TomorrowCardInfo("00:00", "12°", "0%"),
            TomorrowCardInfo("01:00", "12°", "0%"),
            TomorrowCardInfo("02:00", "13°", "5%"),
            TomorrowCardInfo("03:00", "15°", "5%"),
        )

        val adapter = AdapterTomorrowScreen(listItems)
        binding.rvTomorrowScreen.adapter = adapter
        binding.rvTomorrowScreen.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}