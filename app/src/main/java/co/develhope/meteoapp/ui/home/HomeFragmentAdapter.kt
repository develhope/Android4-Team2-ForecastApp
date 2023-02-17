package co.develhope.meteoapp.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.HomeFragmentCardBinding
import co.develhope.meteoapp.databinding.HomeFragmentNextdaysBinding
import co.develhope.meteoapp.databinding.HomeFragmentTitleBinding
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.days

class HomeFragmentAdapter(private val list: List<Data.HomeScreenParts>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val typeTitleHome = 0
    private val typeCardHome = 1
    private val typeNextDaysHome = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            typeTitleHome -> TitleViewHolder(
                HomeFragmentTitleBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            typeCardHome -> CardViewHolder(
                HomeFragmentCardBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            typeNextDaysHome -> NextDaysHolder(
                HomeFragmentNextdaysBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> throw java.lang.IllegalArgumentException("error")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TitleViewHolder -> holder.bind(list[position] as Data.HomeScreenParts.Title)
            is CardViewHolder -> holder.bind(list[position] as Data.HomeScreenParts.Card)
            is NextDaysHolder -> holder.bind(list[position] as Data.HomeScreenParts.Next5DaysString)
        }
    }

    class TitleViewHolder(private val titleBinding: HomeFragmentTitleBinding) :
        RecyclerView.ViewHolder(titleBinding.root) {
        fun bind(title: Data.HomeScreenParts.Title) {
            "${title.titleHome.city},${title.titleHome.region}".also { titleBinding.titleHomeFrag.text = it }
        }
    }

    class CardViewHolder(private val cardBinding: HomeFragmentCardBinding) :
        RecyclerView.ViewHolder(cardBinding.root) {
        fun bind(card: Data.HomeScreenParts.Card) {
            cardBinding.homeCardDayText.text = card.cardInfo.dateTime.dayOfWeek.name
            "${card.cardInfo.minTemp}°".also { cardBinding.minTempGrade.text = it }
            "${card.cardInfo.maxTemp}°".also { cardBinding.maxTempGrade.text = it }
            //FORMAT DATE TIME
            val dateTime = card.cardInfo.dateTime
            val offsetDateTime = OffsetDateTime.ofInstant(dateTime.toInstant(), ZoneOffset.UTC)
            val formattedDate = DateTimeFormatter.ofPattern("dd/MM").format(offsetDateTime)
            cardBinding.dateHomeScreen.text = formattedDate
            //FINISH FORMAT DATE TIME
            cardBinding.weatherImgHome.setImageResource(Data.weatherIcon(card.cardInfo.weather))
            "${card.cardInfo.rainFall}%".also { cardBinding.rainfallNum.text = it }
            "${card.cardInfo.wind}kmh".also { cardBinding.windNum.text = it }
        }
    }

    class NextDaysHolder(private val nextdaysBinding: HomeFragmentNextdaysBinding) :
        RecyclerView.ViewHolder(nextdaysBinding.root) {
        fun bind(nextDays: Data.HomeScreenParts.Next5DaysString) {
            nextdaysBinding.tvNextDays.text = nextDays.next5Days.next5Days
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is Data.HomeScreenParts.Title -> typeTitleHome
            is Data.HomeScreenParts.Card -> typeCardHome
            is Data.HomeScreenParts.Next5DaysString -> typeNextDaysHome
        }
    }
}