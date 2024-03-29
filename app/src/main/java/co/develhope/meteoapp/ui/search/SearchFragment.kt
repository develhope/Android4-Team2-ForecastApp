package co.develhope.meteoapp.ui.search

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.R
import co.develhope.meteoapp.SettingsActivity
import co.develhope.meteoapp.databinding.FragmentSearchBinding
import co.develhope.meteoapp.networking.domainmodel.Place
import co.develhope.meteoapp.ui.utils.GeoLocal
import co.develhope.meteoapp.ui.utils.permission.isLocationEnabledGeo
import co.develhope.meteoapp.ui.utils.permission.speechPermission
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*


class SearchFragment : Fragment() {

    private val REQUEST_CODE_SPEECH_INPUT = 1

    private var _binding: FragmentSearchBinding? = null

    private val viewModel: SearchViewModel by inject()

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
            speechPermission(requireContext(), REQUEST_CODE_SPEECH_INPUT) { speechToText() }
        }
        binding.settings.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            context?.startActivity(intent)
        }

        binding.btnGeoLoc.setOnClickListener {
            try {
                context?.let { it1 -> viewModel.getGeoLocation(it1) }
                if (context?.let { it1 -> isLocationEnabledGeo(it1) } == true){
                    findNavController().navigate(R.id.navigation_home)
                }
            }catch (e:Exception){
                Log.d("GEOLOCAL","ERROR : ${e.cause},${e.message}")
                findNavController().navigate(R.id.navigation_error)
            }
        }
            binding.recyclerViewSearchFrag.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewSearchFrag.setHasFixedSize(true)
            binding.recyclerViewSearchFrag.adapter =
                SearchFragmentAdapter(viewModel.getCityList()) {}
            addCard(viewModel.getCityList())
            searchingCity()

            observerViewModel()
        }

        override fun onResume() {
            super.onResume()
            isMicEnabled()
            isGeoEnabled()
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
                startActivityForResult(
                    intent,
                    REQUEST_CODE_SPEECH_INPUT
                )
            } catch (e: java.lang.Exception) {
                Log.d("Speech", "Error : ${e.message},${e.cause}")
            }
        }

        @Deprecated("Deprecated in Java")
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val res: ArrayList<String> =
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                    binding.SearchBarSearchFrag.setQuery(Objects.requireNonNull(res)[0], false)
                }
            }
        }

        private fun searchingCity() {
            binding.SearchBarSearchFrag.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    Log.d("ERRORE IN SEARCH", "QUI POTREBBE ESSERE L'ERRORE")
                    binding.tvRecentSearch.visibility = View.GONE
                    viewModel.sendingCity(SearchEvents.CitySearched(text.toString()))
                    observerViewModel()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val trimmedQuery = newText?.trim()
                    if (!trimmedQuery.isNullOrEmpty()) {
                        binding.tvRecentSearch.visibility = View.GONE
                        viewModel.sendingCity(SearchEvents.CitySearched(trimmedQuery))
                        observerViewModel()
                    }
                    return false
                }
            })
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun createUISearch(list: List<Place?>) {
            val newlist = ArrayList(list)
            binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(newlist) {
                viewModel.prefsSett(it)
                findNavController().navigate(R.id.navigation_home)
            }
        }

        fun observerViewModel() {
            lifecycleScope.launch {
                viewModel.searchStateLiveData.collect {
                    when (it) {
                        is SearchState.Success -> createUISearch(it.list)
                        is SearchState.Error -> view?.let { it1 ->
                            co.develhope.meteoapp.ui.utils.error(
                                it1
                            )
                        }
                    }
                }
            }
        }


        private fun addCard(list: MutableList<Place?>) {
            list.reverse()
            binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(list) {
                viewModel.getCityObj(it)
                findNavController().navigate(R.id.navigation_home)
            }
        }

        private fun isMicEnabled() {
            val settingsSpeech = viewModel.settingsMicrophone()
            if (settingsSpeech) {
                binding.btnToSpeech.visibility = View.VISIBLE
            } else {
                binding.btnToSpeech.visibility = View.GONE
            }
        }

        private fun isGeoEnabled() {
            val settingsGeo = viewModel.settingsGeo()
            if (settingsGeo) {
                binding.btnGeoLoc.visibility = View.VISIBLE
            } else {
                binding.btnGeoLoc.visibility = View.GONE
            }
        }
    }