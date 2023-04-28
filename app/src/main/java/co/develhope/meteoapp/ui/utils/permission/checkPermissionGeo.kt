package co.develhope.meteoapp.ui.utils.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun checkPermissionGeo(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}