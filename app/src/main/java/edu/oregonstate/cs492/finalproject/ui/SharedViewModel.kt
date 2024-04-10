import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.oregonstate.cs492.finalproject.data.LowDetailMovieAPIResponse

class SharedViewModel : ViewModel() {
    // Live data to hold favorites and watchlist items
    private val _favorites = MutableLiveData<List<LowDetailMovieAPIResponse>>(mutableListOf())
    val favorites: LiveData<List<LowDetailMovieAPIResponse>> = _favorites

    private val _watchlist = MutableLiveData<List<LowDetailMovieAPIResponse>>(mutableListOf())
    val watchlist: LiveData<List<LowDetailMovieAPIResponse>> = _watchlist

    fun addToFavorites(movie: LowDetailMovieAPIResponse) {
        val currentList = _favorites.value?.toMutableList() ?: mutableListOf()
        currentList.add(movie)
        _favorites.value = currentList
    }

    fun addToWatchlist(movie: LowDetailMovieAPIResponse) {
        val currentList = _watchlist.value?.toMutableList() ?: mutableListOf()
        currentList.add(movie)
        _watchlist.value = currentList
    }

    fun removeFromFavorites(movie: LowDetailMovieAPIResponse) {
        val currentList = _favorites.value?.toMutableList() ?: mutableListOf()
        currentList.remove(movie)
        _favorites.value = currentList
    }

    fun removeFromWatchlist(movie: LowDetailMovieAPIResponse) {
        val currentList = _watchlist.value?.toMutableList() ?: mutableListOf()
        currentList.remove(movie)
        _watchlist.value = currentList
    }

}
