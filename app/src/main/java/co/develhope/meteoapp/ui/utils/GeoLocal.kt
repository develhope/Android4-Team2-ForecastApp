package co.develhope.meteoapp.ui.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import co.develhope.meteoapp.di.prefsModule
import co.develhope.meteoapp.networking.domainmodel.Place
import co.develhope.meteoapp.sharedPref.PrefsInterface
import co.develhope.meteoapp.ui.utils.permission.checkPermissionGeo
import co.develhope.meteoapp.ui.utils.permission.isLocationEnabledGeo
import co.develhope.meteoapp.ui.utils.permission.requestPermissionGeo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class GeoLocal(context: Context, val prefs: PrefsInterface) {
    companion object {
        const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    var fusedLocationProvideClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentPosition(context: Context) {
        if (checkPermissionGeo(context)) {
            if (isLocationEnabledGeo(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationProvideClient.lastLocation.addOnCompleteListener {
                    val location: Location? = it.result
                    if (location != null) {
                        Toast.makeText(context, "Localizzato", Toast.LENGTH_SHORT).show()
                        val place = Place(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            city = getCityName(long = location.longitude, lat = location.latitude, context = context),
                            region = getCountryName(long = location.longitude, lat = location.latitude, context = context)
                        )
                        prefs.saveMyCityObject(place)
                    }
                }
            } else {
                Toast.makeText(context, "Attiva localizzazione", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(context, intent, null)
            }
        } else {
            requestPermissionGeo(context, PERMISSION_REQUEST_ACCESS_LOCATION)
        }
    }


    fun onRequestPermissionResult(
        requestCode: Int,
        permission: Array<out String>,
        grantResult: IntArray,
        context: Context
    ) {
        onRequestPermissionResult(requestCode, permission, grantResult, context)
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Granted", Toast.LENGTH_SHORT).show()
                getCurrentPosition(context)
            } else {
                Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getCityName(lat: Double, long: Double, context: Context): String {
        val cityName: String
        val geocoder = Geocoder(context, Locale.ENGLISH)
        val address = geocoder.getFromLocation(lat, long, 1)
        cityName = address?.get(0)?.locality ?: "-"
        return cityName
    }

    private fun getCountryName(lat: Double, long: Double, context: Context): String {
        val countryName: String
        val geocoder = Geocoder(context, Locale.ENGLISH)
        val address = geocoder.getFromLocation(lat, long, 1)
        countryName = address?.get(0)?.countryName ?: "-"
        return countryName
    }

}