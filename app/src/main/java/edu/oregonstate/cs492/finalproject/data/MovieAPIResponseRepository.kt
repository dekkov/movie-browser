package edu.oregonstate.cs492.finalproject.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieAPIResponseRepository (
    private val service: MovieAPIService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadMovieAPIResult(
        apiKey: String,
        title: String?,
        year: String?
    ) : Result<MovieAPIResponse?> =
            withContext(ioDispatcher) {
                try {
                    val response = service.getMovieAPIResult(apikey = apiKey, title = title, year = year)
                    if (response.isSuccessful) {
                        Result.success(response.body())
                    } else {
                        Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error!"))
                    }
                }   catch (e: Exception) {
                    Result.failure(e)
                }
            }

}