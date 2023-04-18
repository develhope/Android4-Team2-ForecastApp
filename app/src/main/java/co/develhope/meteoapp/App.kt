package co.develhope.meteoapp

import android.app.Application
import co.develhope.meteoapp.di.startKoin


class App: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext)

    }
}