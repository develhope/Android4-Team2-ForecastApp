package co.develhope.meteoapp.ui.utils.permission

import android.content.Context
import android.location.LocationManager
import androidx.core.content.ContextCompat.getSystemService


fun isLocationEnabledGeo(context: Context): Boolean {
    val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}