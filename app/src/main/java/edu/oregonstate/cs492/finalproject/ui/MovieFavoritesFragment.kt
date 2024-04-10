package edu.oregonstate.cs492.finalproject.ui

import MovieDao
import SharedViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalproject.R
import edu.oregonstate.cs492.finalproject.data.LowDetailMovieAPIResponse
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieItem
import kotlinx.coroutines.launch
//class MovieFavoritesFragment : Fragment(R.layout.fragment_favorites) {
//    private val viewModel: MovieFavoritesViewModel by viewModels()
//    private lateinit var movieAdapter: MovieAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        movieAdapter = MovieAdapter(
//            onFavoriteClick = { movie ->
//                viewModel.removeFavorite(movie)
//            },
//            onWatchlistClick = { movie ->
//                viewModel.toggleWatchlistStatus(movie)
//            }
//        )
//
//        val movieListRV: RecyclerView = view.findViewById(R.id.rv_movie_list)
//        movieListRV.layoutManager = LinearLayoutManager(requireContext())
//        movieListRV.adapter = movieAdapter
//
//        viewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
//            // Assuming updateMovieList accepts List<TMDBMovieItem>
//            movieAdapter.updateMovieList(movies)
//        }
//    }
//}
class MovieFavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emptyMessageTextView = view.findViewById<TextView>(R.id.tv_empty_message)

        sharedViewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            if (favorites.isEmpty()) {
                emptyMessageTextView.visibility = View.VISIBLE
            } else {
                emptyMessageTextView.visibility = View.GONE
            }
        }

        movieAdapter = MovieAdapter(
            onFavoriteClick = {movie ->
                sharedViewModel.removeFromFavorites(movie)
            },
            onWatchlistClick = { movie ->
                sharedViewModel.addToWatchlist(movie)
            }
        )

        val movieListRV: RecyclerView = view.findViewById(R.id.favoritesRecyclerView)
        movieListRV.layoutManager = LinearLayoutManager(requireContext())
        movieListRV.adapter = movieAdapter

        sharedViewModel.favorites.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateMovieList(movies)
        }
    }
}


class MovieFavoritesViewModel(private val movieDao: MovieDao) : ViewModel() {
    val favoriteMovies: LiveData<List<TMDBMovieItem>> = movieDao.getFavoriteMovies()

    fun toggleWatchlistStatus(movie: TMDBMovieItem) {
        viewModelScope.launch {
            movie.isInWatchlist = !movie.isInWatchlist
            movieDao.updateMovie(movie)
        }
    }

    fun removeFavorite(movie: TMDBMovieItem) {
        viewModelScope.launch {
            movieDao.deleteMovie(movie)
        }
    }
}


