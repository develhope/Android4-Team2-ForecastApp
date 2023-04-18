package co.develhope.meteoapp.sharedPref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.networking.domainmodel.Place
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(context: Context) : PrefsInterface {

    private val CITY_SEARCHED = "city"
    private val HOME_INFO = "home"
    private val LIST_CITY_SEARCHED = "list"
    private val SETTINGS = "enable_microphone_permission"

    private val sharedPermission: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
        context
    )

    private val citySearchedPreferences: SharedPreferences = context.getSharedPreferences(
        CITY_SEARCHED,
        Context.MODE_PRIVATE
    )

    private val homeInfoPreferences: SharedPreferences = context.getSharedPreferences(
        HOME_INFO,
        Context.MODE_PRIVATE
    )

    private val listCitySearchedPreferences: SharedPreferences = context.getSharedPreferences(
        LIST_CITY_SEARCHED,
        Context.MODE_PRIVATE
    )

    override fun saveMyCityObject(place: Place) {
        val editor = citySearchedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(place)
        editor.putString(CITY_SEARCHED, json)
        editor.apply()
    }

    override fun getMyCityObject(): Place? {
        val gson = Gson()
        val json = citySearchedPreferences.getString(CITY_SEARCHED, null)
        return gson.fromJson(json, Place::class.java)
    }

    override fun saveMyHomeObject(home: HomeCardInfo) {
        val editor = homeInfoPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(home)
        editor.putString(HOME_INFO, json)
        editor.apply()
    }

    override fun getMyHomeObject(): HomeCardInfo? {
        val gson = Gson()
        val json = homeInfoPreferences.getString(HOME_INFO, null)
        return gson.fromJson(json, HomeCardInfo::class.java)
    }

    override fun saveMyListCityObject(place: Place) {
        val listPlace = addInfo(place)
        val editor = listCitySearchedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(listPlace)
        editor.putString(LIST_CITY_SEARCHED, json)
        editor.apply()
    }

    override fun getMyListCityObject(): MutableList<Place?> {
        val gson = Gson()
        val json = listCitySearchedPreferences.getString(LIST_CITY_SEARCHED, null)
        val type = object : TypeToken<List<Place?>>() {}.type
        return gson.fromJson(json, type)
            ?: mutableListOf()
    }

    private fun addInfo(place: Place): List<Place?> {
        val listCityInfo = getMyListCityObject()
        if (listCityInfo.contains(place)) {
            return listCityInfo
        } else {
            listCityInfo.add(place)
            if (listCityInfo.size == 6) {
                listCityInfo.removeFirst()
            } else {
                return listCityInfo
            }
            return listCityInfo
        }
    }

        override var isGaranted: Boolean
        get() = sharedPermission.getBoolean(SETTINGS, false)
        set(value) = sharedPermission.edit().putBoolean(SETTINGS, value).apply()
}
