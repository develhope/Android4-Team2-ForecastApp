package co.develhope.meteoapp.sharedPref

import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.networking.domainmodel.Place

interface PrefsInterface {

    fun saveMyCityObject(place: Place)

    fun getMyCityObject(): Place?

    fun saveMyHomeObject(home: HomeCardInfo)

    fun getMyHomeObject(): HomeCardInfo?

    fun saveMyListCityObject(place: Place)

    fun getMyListCityObject(): MutableList<Place?>
}