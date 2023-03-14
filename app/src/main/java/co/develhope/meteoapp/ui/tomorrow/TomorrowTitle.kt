package co.develhope.meteoapp.ui.tomorrow

import org.threeten.bp.OffsetDateTime

data class TomorrowTitle(
            val city: String,
            val region: String,
            val date: OffsetDateTime
        )
