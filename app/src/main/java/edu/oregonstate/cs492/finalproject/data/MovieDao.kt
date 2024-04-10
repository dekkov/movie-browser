import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieItem

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): LiveData<List<TMDBMovieItem>>

    @Query("SELECT * FROM movies WHERE isInWatchlist = 1")
    fun getWatchlistedMovies(): LiveData<List<TMDBMovieItem>>

    @Insert
    suspend fun insertMovie(movie: TMDBMovieItem)

    @Update
    suspend fun updateMovie(movie: TMDBMovieItem)

    @Delete
    suspend fun deleteMovie(movie: TMDBMovieItem)
}
