package co.develhope.meteoapp.networking

import co.develhope.meteoapp.networking.weeklySummary.WeeklySummaryInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object OpenMeteoRetrofitInstance {

    fun interceptor (): Interceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    val BASE_URL = "https://api.open-meteo.com"
    val client = OkHttpClient().newBuilder()
        .addInterceptor(interceptor())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val openMeteoApi : WeeklySummaryInterface = retrofit.create(WeeklySummaryInterface::class.java)



}