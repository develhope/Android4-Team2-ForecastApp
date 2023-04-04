package co.develhope.meteoapp.ui.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import co.develhope.meteoapp.R

fun firstAccess(view: View, context:Context) {
        findNavController(view).navigate(R.id.navigation_search)
        Toast.makeText(
            context,
            "Meteo non disponibile,seleziona una città!",
            Toast.LENGTH_LONG
        ).show()
    }