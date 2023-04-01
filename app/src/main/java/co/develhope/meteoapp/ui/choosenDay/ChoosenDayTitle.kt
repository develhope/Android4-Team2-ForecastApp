package co.develhope.meteoapp.ui.choosenDay

import org.threeten.bp.OffsetDateTime

data class ChoosenDayTitle(
    val city: String? ,
    val region: String? ,
    val date: OffsetDateTime
        )
