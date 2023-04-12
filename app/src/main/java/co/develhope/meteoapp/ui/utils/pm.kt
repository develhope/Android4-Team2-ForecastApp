package co.develhope.meteoapp.ui.utils

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.develhope.meteoapp.R
import co.develhope.meteoapp.ui.error.ErrorFragment
import co.develhope.meteoapp.ui.search.SearchFragment
import com.farimarwat.permissionmate.PMate
import com.farimarwat.permissionmate.PermissionMate
import com.farimarwat.permissionmate.PermissionMateListener
import com.farimarwat.permissionmate.TAG

fun permissionAll(context:Context) : PermissionMate {

    return PermissionMate.Builder(context as AppCompatActivity)
        .setPermissions(
            mutableListOf(
                PMate(
                    Manifest.permission.RECORD_AUDIO,
                    false, "Record audio permission is required to work app correctly"
                )
            ).also {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                    it.add(
                        PMate(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            true, "Storage permission is required to work app correctly"
                        )
                    )
                }
            }
        )
        .setListener(object : PermissionMateListener {
            override fun onPermission(permission: String, granted: Boolean) {
                super.onPermission(permission, granted)
                Log.e(TAG, "Permission: $permission Granted: $granted")
            }

            override fun onError(error: String) {
                super.onError(error)
                Log.e(TAG, error)
            }

            override fun onComplete(permissionsgranted: List<PMate>) {
                super.onComplete(permissionsgranted)
                Log.e(TAG, "Permission Granted: ${permissionsgranted.size}")
            }
        })
        .build()
}