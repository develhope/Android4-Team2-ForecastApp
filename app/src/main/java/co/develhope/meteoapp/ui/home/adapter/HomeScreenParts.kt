package co.develhope.meteoapp.ui.home.adapter

import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo

sealed class HomeScreenParts() {
        data class Title(val titleHome: HomeTitle) : HomeScreenParts()
        data class Card(val cardInfo: HomeCardInfo) : HomeScreenParts()
        data class Next5DaysString(val next5Days: Home5NextDays) : HomeScreenParts()
    }