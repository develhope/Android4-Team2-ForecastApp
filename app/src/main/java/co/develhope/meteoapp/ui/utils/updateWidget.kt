package co.develhope.meteoapp.ui.utils

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import co.develhope.meteoapp.R
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.ui.home.HomeFragment
import co.develhope.meteoapp.ui.widget.WeatherWidget

@RequiresApi(Build.VERSION_CODES.S)
fun updateWidget(
    context: Context,
    widgetCity: String?,
    widgetRegion: String?,
    widgetWeather: Weather?,
    widgeDegree: Int?
) {
    val widgetImage = widgetWeather?.let { weatherIcon(it) }
    val views = RemoteViews(context.packageName, R.layout.wheater_widget)
    views.setTextViewText(R.id.appwidget_city, "${widgetCity},${widgetRegion}")
    if (widgetImage != null) {
        views.setImageViewResource(R.id.img_widget, widgetImage)
    }
    views.setTextViewText(R.id.appwidget_degree, "${widgeDegree.toString()}°")
    val widgetIds = AppWidgetManager.getInstance(context)
        .getAppWidgetIds(ComponentName(context, WeatherWidget::class.java))
    AppWidgetManager.getInstance(context).updateAppWidget(widgetIds, views)
}