package co.develhope.meteoapp.ui.domani

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.TomorrowScreenRowCardBinding
import co.develhope.meteoapp.databinding.TomorrowScreenTitleBinding
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class AdapterTomorrowScreen (

    private val items: List<Data.TomorrowScreenData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val forecast_tomorrow_screen = 0
    private val title_tomorrow_screen = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){

            forecast_tomorrow_screen ->
                Tomorrow_Row_Card_ViewHolder(TomorrowScreenRowCardBinding.inflate
                    (LayoutInflater.from(parent.context), parent, false))

            title_tomorrow_screen ->
                Tomorrow_Title_ViewHolder(TomorrowScreenTitleBinding.inflate
                    (LayoutInflater.from(parent.context), parent, false))

            else -> throw java.lang.IllegalArgumentException("ERROR")
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is Tomorrow_Row_Card_ViewHolder -> holder.bind(items[position] as Data.TomorrowScreenData.TSForecast)
            is Tomorrow_Title_ViewHolder -> holder.bind(items[position] as Data.TomorrowScreenData.TSTitle)
        }

    }

    class Tomorrow_Row_Card_ViewHolder(private val rowCardBinding: TomorrowScreenRowCardBinding)
        : RecyclerView.ViewHolder(rowCardBinding.root) {

            fun bind(card: Data.TomorrowScreenData.TSForecast){

                //ROW DATA

               // val dateTime = card.forecast_data.date
                //val offsetDateTime = OffsetDateTime.ofInstant(dateTime.toInstant(), ZoneOffset.ofHours(1))
               // val formattedDate = DateTimeFormatter.ofPattern("hh mm").format(offsetDateTime)

                rowCardBinding.tvHour.text = itemView.context.getString(
                    R.string.tv_hour_00, card.forecast_data.date.hour
                )

                rowCardBinding.tvWeatherImage.setImageResource(Data.weatherIcon(card.forecast_data.weather))

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

            }
        }


    class Tomorrow_Title_ViewHolder(private val titleBinding: TomorrowScreenTitleBinding)
        : RecyclerView.ViewHolder(titleBinding.root) {

            fun bind(title: Data.TomorrowScreenData.TSTitle){
                titleBinding.tvCityName.text = title.titleTomorrow.city
                titleBinding.tvRegionName.text = title.titleTomorrow.region
                val dateTime2 = title.titleTomorrow.date
                val offsetDateTime2 = OffsetDateTime.ofInstant(dateTime2.toInstant() , ZoneOffset.UTC)
                val formattedDate2 = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(offsetDateTime2.plusDays(1))
                titleBinding.tvDate.text = formattedDate2
                }
            }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Data.TomorrowScreenData.TSForecast -> forecast_tomorrow_screen
            is Data.TomorrowScreenData.TSTitle -> title_tomorrow_screen
        }
    }
}