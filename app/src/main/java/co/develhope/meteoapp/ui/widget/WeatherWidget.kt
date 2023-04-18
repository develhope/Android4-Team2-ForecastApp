package co.develhope.meteoapp.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import co.develhope.meteoapp.R
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.sharedPref.PrefsInterface
import co.develhope.meteoapp.ui.utils.weatherIcon

/**
 * Implementation of App Widget functionality.
 */
class WeatherWidget(val pref: PrefsInterface) : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, pref)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int, prefs: PrefsInterface
) {
    val widgetCity = prefs.getMyCityObject()?.city ?: "Scegli"
    val widgetRegion = prefs.getMyCityObject()?.region ?: "Città"
    val widgetImage = weatherIcon(prefs.getMyHomeObject()?.weather ?: Weather.UNKNOWN)
    val widgetDegree = prefs.getMyHomeObject()?.maxTemp ?: 0
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.wheater_widget)
    views.setTextViewText(R.id.appwidget_city, "${widgetCity},${widgetRegion}")
    views.setImageViewResource(R.id.img_widget, widgetImage)
    views.setTextViewText(R.id.appwidget_degree, "${widgetDegree}°")


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}