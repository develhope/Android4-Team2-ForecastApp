package co.develhope.meteoapp.ui.utils.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun speechPermission(context: Context,REQUEST_CODE_SPEECH_INPUT:Int,speech :() -> Unit) {
        if (context.let {
                ActivityCompat.checkSelfPermission(
                    it, Manifest.permission.RECORD_AUDIO
                )
            }
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_CODE_SPEECH_INPUT
            )
        } else {
            speech()
        }
    }

