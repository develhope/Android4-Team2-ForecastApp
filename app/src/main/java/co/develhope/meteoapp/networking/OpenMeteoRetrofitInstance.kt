package co.develhope.meteoapp.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenMeteoRetrofitInstance {

    private fun retrofitGetData() {
        val logging = HttpLoggingInterceptor()
        val authorizationInterceptor = AuthorizationInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authorizationInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.open-meteo.com")
            .build()
    }
}