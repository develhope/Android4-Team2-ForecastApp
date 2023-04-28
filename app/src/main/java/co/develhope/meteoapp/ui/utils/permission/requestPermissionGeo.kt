package co.develhope.meteoapp.ui.utils.permission

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat

fun requestPermissionGeo(context: Context,PERMISSION_REQUEST_ACCESS_LOCATION:Int) {
    ActivityCompat.requestPermissions(
        context as Activity, arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ),PERMISSION_REQUEST_ACCESS_LOCATION
    )
}