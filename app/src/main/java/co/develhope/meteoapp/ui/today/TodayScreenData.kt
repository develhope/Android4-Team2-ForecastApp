package co.develhope.meteoapp.ui.today

import co.develhope.meteoapp.networking.domainmodel.TodayCardData

sealed class TodayScreenData() {
    data class TodayScreenTitle(val todayTitle: TodayTitleData) : TodayScreenData()
    data class TodayScreenCard(val todayCard : TodayCardData) : TodayScreenData()
}