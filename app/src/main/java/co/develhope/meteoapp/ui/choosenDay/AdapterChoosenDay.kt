package co.develhope.meteoapp.ui.choosenDay

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.ChoosenDayScreenRowCardBinding
import co.develhope.meteoapp.databinding.ChoosenDayScreenTitleBinding
import co.develhope.meteoapp.di.prefsModule
import co.develhope.meteoapp.sharedPref.PrefsInterface
import co.develhope.meteoapp.ui.utils.weatherIcon
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.temporal.ChronoField
import java.util.*

class AdapterChoosenDay(

    private val items: List<ChoosenDayScreenData>,val prefs: PrefsInterface
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val forecast_choosen_screen = 0
    private val title_choosen_screen = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {

            forecast_choosen_screen ->
                ChoosenRowCardViewHolder(
                    ChoosenDayScreenRowCardBinding.inflate
                        (LayoutInflater.from(parent.context), parent, false)
                )

            title_choosen_screen ->
                ChoosenTitleViewHolder(
                    ChoosenDayScreenTitleBinding.inflate
                        (LayoutInflater.from(parent.context), parent, false),prefs
                )

            else -> throw java.lang.IllegalArgumentException("ERROR")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChoosenRowCardViewHolder -> holder.bind(items[position] as ChoosenDayScreenData.TSForecast)
            is ChoosenTitleViewHolder -> holder.bind(items[position] as ChoosenDayScreenData.TSTitle)
        }
    }

    class ChoosenRowCardViewHolder(private val rowCardBinding: ChoosenDayScreenRowCardBinding) :
        RecyclerView.ViewHolder(rowCardBinding.root) {

        fun bind(card: ChoosenDayScreenData.TSForecast) {

            rowCardBinding.tvHour.text = itemView.context.getString(
                R.string.tv_hour_00, card.forecast_data.date.hour
            )

            rowCardBinding.tvWeatherImage.setImageResource(weatherIcon(card.forecast_data.weather))

            rowCardBinding.tvTemperature.text = itemView.context.getString(
                R.string.tv_temperature, card.forecast_data.temperature
            )

            rowCardBinding.tvPercentageRain.text = itemView.context.getString(
                R.string.tv_humRainCov, card.forecast_data.precipitation
            )

            //CARD DATA

            rowCardBinding.PerceivedTemp.text = itemView.context.getString(
                R.string.tv_temperature, card.forecast_data.perc_temperature
            )

            rowCardBinding.UVInd.text = itemView.context.getString(
                R.string.tv_uvIndex, card.forecast_data.UV_Index
            )

            rowCardBinding.Humidity.text = itemView.context.getString(
                R.string.tv_humRainCov, card.forecast_data.humidity
            )

            rowCardBinding.Wind.text = itemView.context.getString(
                R.string.tv_kmWind, card.forecast_data.wind
            )

            rowCardBinding.Coverage.text = itemView.context.getString(
                R.string.tv_humRainCov, card.forecast_data.coverage
            )

            rowCardBinding.Rain.text = itemView.context.getString(
                R.string.tv_percRain, card.forecast_data.rain
            )


            rowCardBinding.tvImageArrow.setOnClickListener {
                if (rowCardBinding.CardViewTomorrow.visibility == View.GONE) {
                    TransitionManager.beginDelayedTransition(
                        rowCardBinding.CardViewTomorrow,
                        AutoTransition()
                    )
                    rowCardBinding.CardViewTomorrow.visibility = View.VISIBLE
                    rowCardBinding.tvImageArrow.rotation = 180F
                    rowCardBinding.firstLine.visibility = View.GONE
                } else {
                    TransitionManager.beginDelayedTransition(
                        rowCardBinding.CardViewTomorrow,
                        AutoTransition()
                    )
                    rowCardBinding.CardViewTomorrow.visibility = View.GONE
                    rowCardBinding.firstLine.visibility = View.VISIBLE
                    rowCardBinding.tvImageArrow.rotation = 0F
                }
            }
        }
    }

    class ChoosenTitleViewHolder(private val titleBinding: ChoosenDayScreenTitleBinding, private val prefs: PrefsInterface) :
        RecyclerView.ViewHolder(titleBinding.root) {

        fun bind(title: ChoosenDayScreenData.TSTitle) {
            "${title.title_choosenDay.city}, ${title.title_choosenDay.region}".also {
                titleBinding.cvTitleNameChoosen.text = it
            }
            val weekChoosen = DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_WEEK)
                .toFormatter(Locale.getDefault())
                .format(prefs.getMyHomeObject()?.dateTime)
                .capitalize(Locale.ROOT)
            "$weekChoosen,".also { titleBinding.cvChoosen.text = it }
            val formattedDate2 = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                .format(prefs.getMyHomeObject()?.dateTime)
            titleBinding.cvDate.text = formattedDate2
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ChoosenDayScreenData.TSForecast -> forecast_choosen_screen
            is ChoosenDayScreenData.TSTitle -> title_choosen_screen
            else -> throw java.lang.IllegalArgumentException("ERROR")
        }
    }
}

