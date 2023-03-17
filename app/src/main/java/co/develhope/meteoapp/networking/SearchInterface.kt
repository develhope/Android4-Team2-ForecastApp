package co.develhope.meteoapp.networking

import co.develhope.meteoapp.networking.dto.SearchData
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchInterface {

    @GET("v1/search")
    suspend fun getSearchData(
        @Query("name") name: String?
    ) : SearchData
}