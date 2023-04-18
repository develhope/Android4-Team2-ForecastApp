package co.develhope.meteoapp.di

import android.content.Context
import co.develhope.meteoapp.sharedPref.Prefs
import co.develhope.meteoapp.sharedPref.PrefsInterface
import co.develhope.meteoapp.ui.choosenDay.AdapterChoosenDay
import co.develhope.meteoapp.ui.choosenDay.ChoosenDayViewModel
import co.develhope.meteoapp.ui.home.HomeViewModel
import co.develhope.meteoapp.ui.search.SearchViewModel
import co.develhope.meteoapp.ui.today.TodayViewModel
import co.develhope.meteoapp.ui.tomorrow.TomorrowViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.logger.Level
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { TomorrowViewModel(get()) }
    viewModel { TodayViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { ChoosenDayViewModel(get()) }
}

val prefsModule = module {
    single<PrefsInterface> {
        Prefs(get())
    }
}

fun startKoin(context: Context) {
    org.koin.core.context.startKoin {
        androidLogger(Level.ERROR)
        androidContext(context)
        modules(
            listOf(
                viewModelModule, prefsModule
            )
        )
    }
}

