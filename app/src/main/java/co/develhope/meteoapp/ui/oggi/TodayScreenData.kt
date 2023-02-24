package co.develhope.meteoapp.ui.oggi

sealed class TodayScreenData() {
    data class TodayScreenTitle(val todayTitle: TodayTitleData) : TodayScreenData()
    data class TodayScreenCard(val todayCard : TodayCardData) : TodayScreenData()
}