package edu.oregonstate.cs492.finalproject.data.TMDBAPI

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeSource

class TMDBMovieRepository(
    private val service: TMDBMovieService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO)
{
//    private var currentLocation: String? = null
//    private var currentUnits: String? = null
    private var cacheMovie: TMDBMovie? = null

    private val cacheMaxAge = 5.minutes
    private val timeSource = TimeSource.Monotonic
    private var timeStamp = timeSource.markNow()

    suspend fun loadTMDBMovie(
        apikey: String
    ) : Result<TMDBMovie?> =
            withContext(ioDispatcher) {
                try {
                    val response = service.loadTMDBMovie(apikey)
                    if (response.isSuccessful) {
                        print("SUCCESS")
                        cacheMovie = response.body()
                        timeStamp = timeSource.markNow()
                        Result.success(cacheMovie)
                    } else {
                        print("Failure")
                        Result.failure(Exception(response.errorBody()?.string()))
                    }
                } catch (e: Exception) {
                    Result.failure(e)
                }
                finally {
                    val response = service.loadTMDBMovie(apikey)
                }
            }

}