package edu.oregonstate.cs492.finalproject.data.TMDBAPI


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class TMDBMovie(
    val results: List<TMDBMovieItem>
)
