package co.develhope.meteoapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.HomeFragmentCardBinding
import co.develhope.meteoapp.databinding.HomeFragmentNextdaysBinding
import co.develhope.meteoapp.databinding.HomeFragmentTitleBinding
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter


class HomeFragmentAdapter(private val list: List<HomeScreenParts>) :
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
            is CardViewHolder -> holder.bind(list[position] as HomeScreenParts.Card, context = holder.itemView.context)
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
        fun bind(card: HomeScreenParts.Card,context: Context) {
            //TODAY OR TOMORROW DAY
            if (card.cardInfo.dateTime.toLocalDate() == OffsetDateTime.now().toLocalDate()) {
                cardBinding.homeCardDayText.text = context.getString(R.string.today)
            } else if (card.cardInfo.dateTime.toLocalDate() == OffsetDateTime.now().plusDays(1)
                    .toLocalDate()
            ) {
                cardBinding.homeCardDayText.text = context.getString(R.string.tomorrow)
            } else {
                cardBinding.homeCardDayText.text = card.cardInfo.dateTime.dayOfWeek.name
            }

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
            //CLICK TO SWITCH FRAGMENT
            cardBinding.cardId.setOnClickListener {
                val choosenFragment =
                    when (card.cardInfo.cardSwitch) {
                        ESwitchFragCard.OGGI_FRAG -> R.id.navigation_oggi
                        ESwitchFragCard.DOMANI_FRAG -> R.id.navigation_domani
                    }
                it.findNavController().navigate(choosenFragment)
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

