package co.develhope.meteoapp.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m")
            .get()
            .build()

        return client.newCall(request).execute()
    }
}