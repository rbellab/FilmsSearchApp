package eu.berngardt.filmssearch.data

import eu.berngardt.filmssearch.data.Entity.TmdbResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET(ApiConstants.POPULAR_MOVIES_DATA_SUB_URL)
    fun getFilms(
        @Query(QUERY_API_KEY) apiKey: String,
        @Query(QUERY_LANGUAGE) language: String,
        @Query(QUERY_PAGE) page: Int
    ): Call<TmdbResults>

    companion object {
        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_LANGUAGE = "language"
        private const val QUERY_PAGE = "page"
    }
}