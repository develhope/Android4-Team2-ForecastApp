package co.develhope.meteoapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import co.develhope.meteoapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        val window = this.window
        if (window != null) {
            window.statusBarColor = this.getColor(R.color.bg_Home) ?: 0

            window.decorView.setSystemUiVisibility(0)

        }

     Handler().postDelayed({
         val intent = Intent(this,MainActivity::class.java)
         startActivity(intent)
         finish()
     },2000)
    }
}