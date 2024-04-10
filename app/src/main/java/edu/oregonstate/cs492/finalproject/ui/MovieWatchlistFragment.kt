package edu.oregonstate.cs492.finalproject.ui

import SharedViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalproject.R
import edu.oregonstate.cs492.finalproject.data.LowDetailMovieAPIResponse

class MovieWatchlistFragment : Fragment(R.layout.fragment_watchlist) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emptyMessageTextView = view.findViewById<TextView>(R.id.tv_empty_message)

        sharedViewModel.favorites.observe(viewLifecycleOwner) { watchlist ->
            if (watchlist.isEmpty()) {
                emptyMessageTextView.visibility = View.VISIBLE
            } else {
                emptyMessageTextView.visibility = View.GONE
            }
        }

        movieAdapter = MovieAdapter(
            onFavoriteClick = { movie ->
                sharedViewModel.addToFavorites(movie)
            },
            onWatchlistClick = { movie ->
                sharedViewModel.removeFromWatchlist(movie)
            }
        )

        val movieListRV: RecyclerView = view.findViewById(R.id.watchlistRecyclerView)
        movieListRV.layoutManager = LinearLayoutManager(requireContext())
        movieListRV.adapter = movieAdapter

        sharedViewModel.watchlist.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateMovieList(movies)
        }
    }
}

// for database. TODO enough if time to
//class MovieWatchlistViewModel : ViewModel() {
//    private val _watchlistMovies = MutableLiveData<List<LowDetailMovieAPIResponse>>()
//    val watchlistMovies: LiveData<List<LowDetailMovieAPIResponse>> = _watchlistMovies
//
//    fun loadWatchlistMovies() {
//        // Logic to load and set watchlist movies
////        _watchlistMovies.value =
//    }
//}

