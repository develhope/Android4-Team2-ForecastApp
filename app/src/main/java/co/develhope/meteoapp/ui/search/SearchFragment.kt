package co.develhope.meteoapp.ui.search

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentSearchBinding
import co.develhope.meteoapp.networking.domainmodel.Place
import java.security.Permission
import java.util.*


class SearchFragment : Fragment() {

    private val REQUEST_CODE_SPEECH_INPUT = 1

    private var _binding: FragmentSearchBinding? = null

    private val viewModel: SearchViewModel by viewModels()

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
            if (context?.let { it1 ->
                    ActivityCompat.checkSelfPermission(
                        it1, Manifest.permission.RECORD_AUDIO
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
               val buttonToSpeech = binding.btnToSpeech
                buttonToSpeech.visibility = View.GONE
            } else {
                speechToText()
            }
        }

        binding.recyclerViewSearchFrag.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSearchFrag.setHasFixedSize(true)
        binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(mutableListOf()) {}

        addCard(Data.listCitySearched)

        searchingCity()
        observerViewModel()
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

    private fun searchingCity() {
        binding.SearchBarSearchFrag.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                binding.tvRecentSearch.visibility = View.GONE
                viewModel.sendingCity(SearchEvents.CitySearched(text.toString()))
                observerViewModel()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.tvRecentSearch.visibility = View.GONE
                viewModel.sendingCity(SearchEvents.CitySearched(newText.toString()))
                observerViewModel()
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun speechToText() {
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


    fun createUISearch(list: List<Place?>) {
        val newlist = ArrayList(list)
        binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(newlist) {
            Data.citySearched = it
            Data.listCitySearched.add(it)
            findNavController().navigate(R.id.navigation_home)
        }
    }

    fun observerViewModel() {
        viewModel.searchStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is SearchState.Success -> createUISearch(it.list)
                is SearchState.Error -> view?.let { it1 -> co.develhope.meteoapp.ui.utils.error(it1) }
            }
        }
    }

    private fun addCard(list: MutableList<Place?>){
        binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(list) {
            Data.citySearched = it
            findNavController().navigate(R.id.navigation_home)
        }
    }
}