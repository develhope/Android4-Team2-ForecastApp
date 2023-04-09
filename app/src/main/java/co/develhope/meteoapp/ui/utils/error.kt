package co.develhope.meteoapp.ui.utils

import android.view.View
import androidx.navigation.Navigation.findNavController
import co.develhope.meteoapp.R

fun error(view: View){
        findNavController(view).navigate(R.id.navigation_error)
    }