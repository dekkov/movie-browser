package edu.oregonstate.cs492.finalproject.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {
    @GET("?")
    suspend fun getMovieAPIResult(
        @Query("apikey") apikey: String,
        @Query("t") title: String?,
        @Query("y") year: String?,
    ): Response<MovieAPIResponse>

    @GET("?")
    suspend fun getMovieListAPIResult(
        @Query("apikey") apikey: String,
        @Query("s") title: String?,
        @Query("y") year: String?,
    ): Response<MovieListAPIResponse>

    companion object {
        private const val BASE_URL = "https://www.omdbapi.com"

        fun create(): MovieAPIService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(MovieAPIService::class.java)
        }

    }
}