package co.develhope.meteoapp.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenMeteoRetrofitInstance {
    private fun retrofitGetData(){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.open-meteo.com")
            .build()
    }
}