package co.develhope.meteoapp.ui.today

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.TodayRowCardBinding
import co.develhope.meteoapp.databinding.TodayTitleFragmentBinding
import co.develhope.meteoapp.ui.utils.weatherIcon
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

class TodayScreenAdapter (var items : List<TodayScreenData>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val today_title = 0
    private val today_card = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            today_title ->
                TodayTitleViewHolder(
                    TodayTitleFragmentBinding.inflate
                        (LayoutInflater.from(parent.context), parent, false)
                )

            today_card ->
                TodayRowCardViewHolder(
                    TodayRowCardBinding.inflate
                        (LayoutInflater.from(parent.context), parent, false)
                )


            else -> throw java.lang.IllegalArgumentException("ERROR")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodayRowCardViewHolder ->
                holder.bind(items[position] as TodayScreenData.TodayScreenCard)
            is TodayTitleViewHolder ->
                holder.bind(items[position] as TodayScreenData.TodayScreenTitle)
        }

    }

    class TodayRowCardViewHolder(private val rowCardBinding: TodayRowCardBinding) :
        RecyclerView.ViewHolder(rowCardBinding.root) {
        fun bind(card: TodayScreenData.TodayScreenCard) {
            rowCardBinding.todayHour.text =
                itemView.context.getString(R.string.today_hour, card.todayCard.date.hour)
            rowCardBinding.todayWeather.setImageResource(weatherIcon(card.todayCard.weather))
            rowCardBinding.todayTemperature.text =
                itemView.context.getString(R.string.today_temperature, card.todayCard.temperature)
            rowCardBinding.todayPrecipitationsView.setImageResource(R.drawable.drop)
            rowCardBinding.todayPrecipitationsText.text =
                itemView.context.getString(R.string.today_precipitations, card.todayCard.humidity)

            rowCardBinding.todayCardPerceivedVal.text=
                itemView.context.getString(R.string.today_perceived, card.todayCard.perc_temperature)
            rowCardBinding.todayCardUvVal.text=
                itemView.context.getString(R.string.today_uv, card.todayCard.UV_Index)
            rowCardBinding.todayCardHumidityVal.text=
                itemView.context.getString(R.string.today_humidity, card.todayCard.humidity)
            rowCardBinding.todayCardWindVal.text=
                itemView.context.getString(R.string.today_wind, card.todayCard.wind)
            rowCardBinding.todayCardCoverageVal.text=
                itemView.context.getString(R.string.today_coverage, card.todayCard.coverage)
            rowCardBinding.todayCardRainVal.text=
                itemView.context.getString(R.string.today_rain, card.todayCard.rain)


        }
    }

    class TodayTitleViewHolder(private val titleFragmentBinding: TodayTitleFragmentBinding) :
        RecyclerView.ViewHolder(titleFragmentBinding.root) {
        fun bind(title: TodayScreenData.TodayScreenTitle) {
            titleFragmentBinding.todayTitleCity.text = title.todayTitle.city
            titleFragmentBinding.todayTitleRegion.text = title.todayTitle.region
            val dateTimeT = title.todayTitle.date
            val offsetDateTimeT = org.threeten.bp.OffsetDateTime.ofInstant(dateTimeT.toInstant(), ZoneOffset.UTC)
            val formattedDate = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(offsetDateTimeT)
            titleFragmentBinding.todayTitleDate.text = formattedDate

        }

    }

   override fun getItemViewType(position: Int): Int {
        return when (items[position]){
            is TodayScreenData.TodayScreenTitle -> today_title
            is TodayScreenData.TodayScreenCard -> today_card
        }
    }
}






