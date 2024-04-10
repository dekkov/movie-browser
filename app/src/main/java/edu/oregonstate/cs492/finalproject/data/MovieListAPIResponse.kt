package edu.oregonstate.cs492.finalproject.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//JSON parser for the simplified, list-version of the OMDB API's response
@JsonClass(generateAdapter = true)
data class MovieListAPIResponse(
    @Json(name = "Search") val movies: List<LowDetailMovieAPIResponse>
)

@JsonClass(generateAdapter = true)
data class LowDetailMovieAPIResponse(
    @Json(name = "Title") val title: String,
    @Json(name = "Year") val year: String,
    @Json(name = "imdbID") val imdbID: String,
    @Json(name = "Type") val type: String,
    @Json(name = "Poster") val poster: String,
)