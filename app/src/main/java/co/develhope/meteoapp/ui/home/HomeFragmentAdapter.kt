package co.develhope.meteoapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.HomeFragmentCardBinding
import co.develhope.meteoapp.databinding.HomeFragmentNextdaysBinding
import co.develhope.meteoapp.databinding.HomeFragmentTitleBinding
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.ui.utils.weatherIcon
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.temporal.ChronoField
import java.util.*


class HomeFragmentAdapter(private val list: List<HomeScreenParts>, private val onClick: (HomeCardInfo)-> Unit) :
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
        when (holder) {
            is TitleViewHolder -> holder.bind(list[position] as HomeScreenParts.Title)
            is CardViewHolder -> holder.bind(list[position] as HomeScreenParts.Card, context = holder.itemView.context,onClick)
            is NextDaysHolder -> holder.bind(list[position] as HomeScreenParts.Next5DaysString)
        }
    }

    class TitleViewHolder(private val titleBinding: HomeFragmentTitleBinding) :
        RecyclerView.ViewHolder(titleBinding.root) {
        fun bind(title: HomeScreenParts.Title) {
            "${title.titleHome.city},${title.titleHome.region}".also {
                titleBinding.titleHomeFrag.text = it
            }
        }
    }

    class CardViewHolder(private val cardBinding: HomeFragmentCardBinding) :
        RecyclerView.ViewHolder(cardBinding.root) {
        fun bind(card: HomeScreenParts.Card, context: Context, onClick: (HomeCardInfo) -> Unit) {
            //TODAY OR TOMORROW DAY
            if (card.cardInfo.dateTime.toLocalDate() == OffsetDateTime.now().toLocalDate()) {
                cardBinding.homeCardDayText.text = context.getString(R.string.today)
            } else if (card.cardInfo.dateTime.toLocalDate() == OffsetDateTime.now().plusDays(1)
                    .toLocalDate()
            ) {
                cardBinding.homeCardDayText.text = context.getString(R.string.tomorrow)
            } else {
                cardBinding.homeCardDayText.text =  DateTimeFormatterBuilder()
                    .appendText(ChronoField.DAY_OF_WEEK)
                    .toFormatter(Locale.getDefault())
                    .format(card.cardInfo.dateTime)
                    .uppercase()
            }

            "${card.cardInfo.minTemp}°".also { cardBinding.minTempGrade.text = it }
            "${card.cardInfo.maxTemp}°".also { cardBinding.maxTempGrade.text = it }
            //FORMAT DATE TIME
            val dateTime = card.cardInfo.dateTime
            val offsetDateTime = OffsetDateTime.ofInstant(dateTime.toInstant(), ZoneOffset.UTC)
            val formattedDate = DateTimeFormatter.ofPattern("dd/MM").format(offsetDateTime)
            cardBinding.dateHomeScreen.text = formattedDate
            //FINISH FORMAT DATE TIME

            cardBinding.weatherImgHome.setImageResource(weatherIcon(card.cardInfo.weather))
            "${card.cardInfo.rainFall} mm".also { cardBinding.rainfallNum.text = it }
            "${card.cardInfo.wind}kmh".also { cardBinding.windNum.text = it }
            //CLICK TO SWITCH FRAGMENT --- TRY SEALED CLASS(EVENTS)
            cardBinding.cardId.setOnClickListener {
                onClick(card.cardInfo)
            }

        }

    }

    class NextDaysHolder(private val nextdaysBinding: HomeFragmentNextdaysBinding) :
        RecyclerView.ViewHolder(nextdaysBinding.root) {
        fun bind(nextDays: HomeScreenParts.Next5DaysString) {
            nextdaysBinding.tvNextDays.text = nextDays.next5Days.next5Days
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is HomeScreenParts.Title -> typeTitleHome
            is HomeScreenParts.Card -> typeCardHome
            is HomeScreenParts.Next5DaysString -> typeNextDaysHome
        }
    }

}