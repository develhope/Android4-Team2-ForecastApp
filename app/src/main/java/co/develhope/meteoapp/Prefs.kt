package co.develhope.meteoapp

import android.content.Context
import android.content.SharedPreferences
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.networking.domainmodel.Place
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(context: Context) {
    // var listCitySearched : MutableList<Place?> = mutableListOf()

    private val CITY_SEARCHED = "city"
    private val HOME_INFO = "home"
    private val LIST_CITY_SEARCHED = "list"

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

    fun saveMyCityObject(place: Place) {
        val editor = citySearchedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(place)
        editor.putString(CITY_SEARCHED, json)
        editor.apply()
    }

    fun getMyCityObject(): Place? {
        val gson = Gson()
        val json = citySearchedPreferences.getString(CITY_SEARCHED, null)
        return gson.fromJson(json, Place::class.java)
    }

    fun saveMyHomeObject(home: HomeCardInfo) {
        val editor = homeInfoPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(home)
        editor.putString(HOME_INFO, json)
        editor.apply()
    }

    fun getMyHomeObject(): HomeCardInfo? {
        val gson = Gson()
        val json = homeInfoPreferences.getString(HOME_INFO, null)
        return gson.fromJson(json, HomeCardInfo::class.java)
    }

    fun saveMyListCityObject(listPlace: MutableList<Place?>) {
        val editor = listCitySearchedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(listPlace)
        editor.putString(LIST_CITY_SEARCHED, json)
        editor.apply()
    }

    fun getMyListCityObject(): MutableList<Place?> {
        val gson = Gson()
        val json = listCitySearchedPreferences.getString(LIST_CITY_SEARCHED, null)
        val type = object : TypeToken<List<Place?>>() {}.type
        return gson.fromJson(json, type)
            ?: mutableListOf()
    }

    fun addInfo(place: Place?): MutableList<Place?> {
        val listCityInfo = getMyListCityObject()
        if (place in listCityInfo) {
            return listCityInfo
        } else {
            listCityInfo.add(place)
            if (listCityInfo.size == 6){
                listCityInfo.removeFirst()
            }else {
                return listCityInfo
            }
            return listCityInfo
        }
    }
}