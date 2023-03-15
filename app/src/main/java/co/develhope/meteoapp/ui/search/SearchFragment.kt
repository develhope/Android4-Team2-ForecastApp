package co.develhope.meteoapp.ui.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentSearchBinding
import co.develhope.meteoapp.networking.domainmodel.Place
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    private lateinit var searchArrayList: ArrayList<Place>
    private lateinit var city: Array<String>
    private lateinit var weather: Array<String>
    private lateinit var temperature: Array<Int>
    private val REQUEST_CODE_SPEECH_INPUT = 1


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


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = activity?.window
        if (window != null) {
            window.statusBarColor = context?.getColor(R.color.background_screen) ?: 0
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        }

        binding.btnToSpeech.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla adesso")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: java.lang.Exception) {
                Log.d("Speech", "Error : ${e.message},${e.cause}")
            }
        }

        dataInit()
        binding.recyclerViewSearchFrag.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSearchFrag.setHasFixedSize(true)
        binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(searchArrayList) {
            Data.nameCity = it.city
            findNavController().navigate(R.id.navigation_home)
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                binding.SearchBarSearchFrag.setQuery(Objects.requireNonNull(res)[0], false)
            }
        }
    }


    private fun dataInit() {
        searchArrayList = arrayListOf()
        city = arrayOf("Roma", "Catania", "Palermo", "Milano", "Bologna")
        weather = arrayOf("rovesci", "nuvoloso", "parz.nuvoloso", "nevoso", "rovesci")
        temperature = arrayOf(8, 9, 8, 2, 4)

        for (i in city.indices) {
            val item = Place(city[i], weather[i], temperature[i])
            searchArrayList.add(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}