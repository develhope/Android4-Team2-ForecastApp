package co.develhope.meteoapp.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.OrientationEventListener
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import co.develhope.meteoapp.R
import co.develhope.meteoapp.networking.domainmodel.Weather
import co.develhope.meteoapp.ui.MainActivity
import co.develhope.meteoapp.ui.home.HomeFragment
import co.develhope.meteoapp.ui.utils.weatherIcon

class WeatherWidget : AppWidgetProvider() {


    @RequiresApi(Build.VERSION_CODES.S)
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
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val widgetCity = "Scegli"
        val widgetRegion = "Città"
        val widgetImage = weatherIcon(Weather.UNKNOWN)
        val widgetDegree = "-"
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName,R.layout.wheater_widget)

        views.setTextViewText(
            R.id.appwidget_city,
            "${widgetCity},${widgetRegion}"
        )
        views.setImageViewResource(R.id.img_widget, widgetImage)
        views.setTextViewText(R.id.appwidget_degree, "${widgetDegree}°")

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        views.setOnClickPendingIntent(R.id.widget_linear_layout, pendingIntent)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
