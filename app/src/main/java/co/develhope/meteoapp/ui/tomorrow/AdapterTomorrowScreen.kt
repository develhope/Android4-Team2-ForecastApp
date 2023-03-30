package co.develhope.meteoapp.ui.tomorrow

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.TomorrowScreenRowCardBinding
import co.develhope.meteoapp.databinding.TomorrowScreenTitleBinding
import co.develhope.meteoapp.ui.utils.weatherIcon
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

class AdapterTomorrowScreen (

    private val items: List<TomorrowScreenData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val forecast_tomorrow_screen = 0
    private val title_tomorrow_screen = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){

            forecast_tomorrow_screen ->
                TomorrowRowCardViewHolder(
                    TomorrowScreenRowCardBinding.inflate
                        (LayoutInflater.from(parent.context), parent, false))

            title_tomorrow_screen ->
                TomorrowTitleViewHolder(
                    TomorrowScreenTitleBinding.inflate
                        (LayoutInflater.from(parent.context), parent, false))

            else -> throw java.lang.IllegalArgumentException("ERROR")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TomorrowRowCardViewHolder -> holder.bind(items[position] as TomorrowScreenData.TSForecast)
            is TomorrowTitleViewHolder -> holder.bind(items[position] as TomorrowScreenData.TSTitle)
        }
    }

    class TomorrowRowCardViewHolder(private val rowCardBinding: TomorrowScreenRowCardBinding)
        : RecyclerView.ViewHolder(rowCardBinding.root) {

        fun bind(card: TomorrowScreenData.TSForecast){

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
                if(rowCardBinding.CardViewTomorrow.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(rowCardBinding.CardViewTomorrow, AutoTransition())
                    rowCardBinding.CardViewTomorrow.visibility = View.VISIBLE
                    rowCardBinding.tvImageArrow.rotation = 180F
                    rowCardBinding.firstLine.visibility = View.GONE
                } else{
                    TransitionManager.beginDelayedTransition(rowCardBinding.CardViewTomorrow, AutoTransition())
                    rowCardBinding.CardViewTomorrow.visibility = View.GONE
                    rowCardBinding.firstLine.visibility = View.VISIBLE
                    rowCardBinding.tvImageArrow.rotation = 0F
                }
            }
        }
    }

    class TomorrowTitleViewHolder(private val titleBinding: TomorrowScreenTitleBinding)
        : RecyclerView.ViewHolder(titleBinding.root) {

        fun bind(title: TomorrowScreenData.TSTitle){
            "${title.titleTomorrow.city}, ${title.titleTomorrow.region}".also {
                titleBinding.tvTitleName.text = it
            }

            val dateTime2 = title.titleTomorrow.date?.plusDays(1)
            val offsetDateTime2 = OffsetDateTime.ofInstant(dateTime2?.toInstant() , ZoneOffset.UTC)
            val formattedDate2 = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(offsetDateTime2.plusDays(1))
            titleBinding.tvDate.text = formattedDate2
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TomorrowScreenData.TSForecast -> forecast_tomorrow_screen
            is TomorrowScreenData.TSTitle -> title_tomorrow_screen
        }
    }
}