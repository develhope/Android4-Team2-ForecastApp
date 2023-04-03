package co.develhope.meteoapp.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.ui.utils.weatherIcon

/**
 * Implementation of App Widget functionality.
 */
class WeatherWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
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
    appWidgetId: Int
) {
    val widgetCity = Data.citySearched?.city?:"Scegli"
    val widgetRegion = Data.citySearched?.region?:"Città"
    val widgetImage = weatherIcon(Data.homeData?.weather ?: Weather.UNKNOWN)
    val widgetDegree = Data.homeData?.maxTemp ?:0
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.wheater_widget)
    views.setTextViewText(R.id.appwidget_city, "${widgetCity},${widgetRegion}")
    views.setImageViewResource(R.id.img_widget,widgetImage)
    views.setTextViewText(R.id.appwidget_degree, "${widgetDegree}°")


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}