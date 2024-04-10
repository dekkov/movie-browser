package edu.oregonstate.cs492.finalproject.data.TMDBAPI

import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
interface TMDBMovieService {

    @GET("now_playing")
    suspend fun loadTMDBMovie(
        @Query("api_key") apiKey: String = "0aa19f793ac2816d2230864c049e6f0e"
    ) : Response<TMDBMovie>

    @GET("top_rated")
    suspend fun loadTMDBMovieTopRated(
        @Query("api_key") apiKey: String = "0aa19f793ac2816d2230864c049e6f0e"
    ) : Response<TMDBMovie>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"

        /**
         * This method can be called as `OpenWeatherService.create()` to create an object
         * implementing the OpenWeatherService interface and which can be used to make calls to
         * the OpenWeather API.
         */
        fun create() : TMDBMovieService {
            val moshi = Moshi.Builder()
                .add(TMDBMovieItemJsonAdapter())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(TMDBMovieService::class.java)
        }

//        fun create() : TMDBMovieService {
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory( ScalarsConverterFactory.create())
//                .build()
//                .create(TMDBMovieService::class.java)
//        }
    }

}