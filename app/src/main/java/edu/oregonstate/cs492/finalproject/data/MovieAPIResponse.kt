package edu.oregonstate.cs492.finalproject.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//JSON parser for the OMDB API's response
//  We likely don't need EVERYTHING here, but I added it JIC
@JsonClass(generateAdapter = true)
data class MovieAPIResponse(
    @Json(name = "Title") val title: String,
    @Json(name = "Year") val year: String,
    @Json(name = "Rated") val mpaRating: String,
    @Json(name = "Released") val released: String,
    @Json(name = "Runtime") val runtime: String,
    @Json(name = "Genre") val genre: String,
    @Json(name = "Director") val director: String,
    @Json(name = "Writer") val writer: String,
    @Json(name = "Actors") val actors: String,
    @Json(name = "Plot") val plot: String,
    @Json(name = "Language") val languages: String,
    @Json(name = "Country") val country: String,
    @Json(name = "Awards") val awards: String,
    @Json(name = "Poster") val poster: String,

    @Json(name = "Ratings") val reviews: List<MovieAPIResponseRating>,

    @Json(name = "Metascore") val metascore: String,
    @Json(name = "imdbRating") val imdbRating: String,
    @Json(name = "imdbVotes") val imdbVotes: String,
    @Json(name = "imdbID") val imdbID: String,
    @Json(name = "Type") val medium: String,
    @Json(name = "DVD") val dvdRelease: String,
    @Json(name = "BoxOffice") val boxOffice: String,
    @Json(name = "Production") val production: String,
    @Json(name = "Website") val website: String,
    @Json(name = "Response") val response: String,
    )

@JsonClass(generateAdapter = true)
data class MovieAPIResponseRating(
    @Json(name = "Source") val source: String,
    @Json(name = "Value") val value: String,
    )