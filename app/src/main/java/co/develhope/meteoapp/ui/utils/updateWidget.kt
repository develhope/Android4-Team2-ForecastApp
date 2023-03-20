package co.develhope.meteoapp.ui.utils

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import co.develhope.meteoapp.R
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.ui.widget.WheaterWidget

fun updateWidget(context: Context, widgetCity: String?, widgetRegion: String?, widgetWeather: Weather, widgetMinTemp: Int?) {
    val widgetImage = weatherIcon(widgetWeather)
    val views = RemoteViews(context.packageName, R.layout.wheater_widget)
    views.setTextViewText(R.id.appwidget_city, "${widgetCity},${widgetRegion}")
    views.setImageViewResource(R.id.img_widget,widgetImage)
    views.setTextViewText(R.id.appwidget_degree, "${widgetMinTemp.toString()}°")
    val widgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context, WheaterWidget::class.java))
    AppWidgetManager.getInstance(context).updateAppWidget(widgetIds, views)
}