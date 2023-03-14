package co.develhope.meteoapp.ui.today

import org.threeten.bp.OffsetDateTime
data class TodayTitleData(
    val city : String,
    val region : String,
    val date : OffsetDateTime
)
