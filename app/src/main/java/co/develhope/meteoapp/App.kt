package co.develhope.meteoapp

import android.app.Application
import co.develhope.meteoapp.sharedPref.Prefs

val prefs: Prefs by lazy {
    App.prefs!!
}

class App: Application(){

    companion object{
        var prefs: Prefs? = null
        lateinit var instance: App
        private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Prefs(applicationContext)
    }
}