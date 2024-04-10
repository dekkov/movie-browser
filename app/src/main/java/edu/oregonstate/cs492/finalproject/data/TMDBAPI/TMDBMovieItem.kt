package edu.oregonstate.cs492.finalproject.data.TMDBAPI

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.io.Serializable

//data class TMDBMovieItem(
//    val id: Int,
//    val backdrop: String,
////    val genre_ids: List<Int>,
//    val title: String,
//    val popularity: Double,
//    val poster_path: String,
//    val overview: String,
//    val released_date: String,
//    val isFavorite: Boolean = false,
//    val isInWatchlist: Boolean = false
//
//) : Serializable
@Entity(tableName = "movies")
data class TMDBMovieItem(
    @PrimaryKey val id: Int,
    val backdrop: String,
//    @Json(name = "genre_ids") @Ignore val genreIds: List<Int>,
    val title: String,
    val popularity: Double,
    @Json(name = "poster_path") val poster_path: String,
    val overview: String,
    @Json(name = "release_date") val released_date: String,
    var isFavorite: Boolean = false,
    var isInWatchlist: Boolean = false
) : Serializable
@JsonClass(generateAdapter = true)
data class TMDBMovieJson(
    val id: Int,
    val title: String,
    val popularity: Double,
    val poster_path: String,
    val overview: String,
    val release_date: String,
    val backdrop_path: String
)

class TMDBMovieItemJsonAdapter {
    @FromJson
    fun TMDBMovieFromJson(list: TMDBMovieJson) = TMDBMovieItem(
        id = list.id,
        title = list.title,
        popularity = list.popularity,
        poster_path = "https://image.tmdb.org/t/p/original/${list.poster_path}",
        overview = list.overview,
        released_date = list.release_date,
        backdrop = "https://image.tmdb.org/t/p/original/${list.backdrop_path}"
    )

    @ToJson
    fun TMDBMovieToJson(forecastPeriod: TMDBMovieItem): String {
        throw UnsupportedOperationException("encoding ForecastPeriod to JSON is not supported")
    }
}

/*
* "genre_ids": [
                14,
                12,
                28
            ],
            "id": 763215,
            "original_language": "en",
            "original_title": "Damsel",
            "overview": "A young woman's marriage to a charming prince turns into a fierce fight for survival when she's offered up as a sacrifice to a fire-breathing dragon.",
            "popularity": 1986.779,
            "poster_path": "/sMp34cNKjIb18UBOCoAv4DpCxwY.jpg",
            "release_date": "2024-03-08",
            "title": "Damsel",
            "video": false,
            "vote_average": 7.289,
            "vote_count": 728
*
*
* */


///