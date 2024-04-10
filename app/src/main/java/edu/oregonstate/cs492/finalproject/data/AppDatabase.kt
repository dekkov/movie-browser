import androidx.room.Database
import androidx.room.RoomDatabase
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieItem

@Database(entities = [TMDBMovieItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}