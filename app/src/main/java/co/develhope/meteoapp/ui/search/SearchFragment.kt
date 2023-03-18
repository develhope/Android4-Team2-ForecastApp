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
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentSearchBinding
import co.develhope.meteoapp.networking.domainmodel.Place
import kotlinx.coroutines.launch
import java.util.*

class SearchFragment : Fragment() {

    private val REQUEST_CODE_SPEECH_INPUT = 1


    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSearchBinding.inflate(inflater , container , false)

        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val window = activity?.window
        if (window != null) {
            window.statusBarColor = context?.getColor(R.color.background_screen) ?: 0
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        }

        binding.btnToSpeech.setOnClickListener {
            permissionRecord()
        }

        binding.recyclerViewSearchFrag.layoutManager =
            LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
        binding.recyclerViewSearchFrag.setHasFixedSize(true)
        binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(emptyList()) {}
        searchCall()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                binding.SearchBarSearchFrag.setQuery(Objects.requireNonNull(res)[0] , false)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun speechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL ,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE , Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT , "Parla adesso")
        try {
            startActivityForResult(intent , REQUEST_CODE_SPEECH_INPUT)
        } catch (e: java.lang.Exception) {
            Log.d("Speech" , "Error : ${e.message},${e.cause}")
        }
    }

    private fun permissionRecord() {
        run {
            if (context?.let {
                    ContextCompat.checkSelfPermission(
                        it , Manifest.permission.RECORD_AUDIO
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity ,
                    arrayOf(Manifest.permission.RECORD_AUDIO) ,
                    REQUEST_CODE_SPEECH_INPUT
                )
            } else {
                // permission already granted
                speechToText()
            }
        }
    }

    private fun searchCall() {
        lifecycleScope.launch {
            try {
                val item: Place = Data.getSearchData("Palermo")
                val item2: Place = Data.getSearchData("Catania")
                val item3: Place = Data.getSearchData("Milano")

                val list: MutableList<Place> = mutableListOf(item, item2,item3)

                binding.recyclerViewSearchFrag.adapter = SearchFragmentAdapter(list) {
                    Data.citySearched = it
                    findNavController().navigate(R.id.navigation_home)
                }

            } catch (e: Exception) {
                Log.d("SearchFragment" , "Error: ${e.message},${e.cause}")

            }
        }
    }
}