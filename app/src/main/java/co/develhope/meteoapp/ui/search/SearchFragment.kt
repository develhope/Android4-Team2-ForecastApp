package co.develhope.meteoapp.ui.oggi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.databinding.FragmentSearchBinding
import co.develhope.meteoapp.ui.search.SearchCardInfo
import co.develhope.meteoapp.ui.search.SearchFragmentAdapter

class SearchFragment : Fragment() {

    private lateinit var searchArrayList: ArrayList<SearchCardInfo>
    private lateinit var city : Array<String>
    private lateinit var weather: Array<String>
    private lateinit var temperature: Array<Int>

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSearchFrag.layoutManager = layoutManager
        binding.recyclerViewSearchFrag.setHasFixedSize(true)
        binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(searchArrayList)


    }

    private fun dataInit(){
        searchArrayList = arrayListOf()
        city = arrayOf("Roma", "Catania", "Palermo", "Milano", "Bologna")
        weather = arrayOf("rovesci", "nuvoloso", "parz.nuvoloso", "nevoso", "rovesci")
        temperature = arrayOf(8, 9, 8, 2, 4)

        for (i in city.indices) {
            val item = SearchCardInfo(city[i] , weather[i] , temperature[i])
            searchArrayList.add(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}