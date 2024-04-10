package edu.oregonstate.cs492.finalproject.ui

import SharedViewModel
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import edu.oregonstate.cs492.finalproject.R
import edu.oregonstate.cs492.finalproject.data.LowDetailMovieAPIResponse

class MovieSearchFragment : Fragment(R.layout.fragment_search) {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val tag = "MovieSearchFragment"

    //private val viewModel: MovieAPIViewModel by viewModels()
    private val viewModel: MovieListAPIViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var loadingErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator


    private lateinit var movieNameTV: TextView
    private lateinit var movieYearTV: TextView
    private lateinit var moviePosterIV: ImageView

    private lateinit var movieResultCard: MaterialCardView

    private lateinit var movieListRV: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(
            onFavoriteClick = { movie ->
                sharedViewModel.addToFavorites(movie)
            },
            onWatchlistClick = { movie ->
                sharedViewModel.addToWatchlist(movie)
            }
        )

        loadingErrorTV = view.findViewById(R.id.tv_loading_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        movieListRV = view.findViewById(R.id.rv_movie_list)
        movieListRV.layoutManager = LinearLayoutManager(requireContext())
        movieListRV.setHasFixedSize(true)
        movieListRV.adapter = movieAdapter

        val searchButton: Button = view.findViewById(R.id.btn_movie_search)
        val searchBoxET: EditText = view.findViewById(R.id.et_search_box)

        //movieNameTV = view.findViewById(R.id.tv_movie_result_name)
        //movieYearTV = view.findViewById(R.id.tv_movie_result_year)
        //moviePosterIV = view.findViewById(R.id.iv_movie_result_poster)
        movieListRV = view.findViewById(R.id.rv_movie_list)
        movieListRV.layoutManager = LinearLayoutManager(requireContext())
        movieListRV.setHasFixedSize(true)
        movieListRV.adapter = movieAdapter
        //movieResultCard = view.findViewById(R.id.mcv_movie_result_card)


        /*
         * Set up an observer on the current movie data.  If the data is not null, display
         * it in the UI.
         */
        viewModel.searchResults.observe(viewLifecycleOwner) { searchResult ->
            if (searchResult != null) {
                /*
                val movieListResult = viewModel.searchResults.value!!
                val movieResult = movieListResult.movies[0] // AN ARRAY YOU CAN ITERATE ACROSS NOW!!
                val ctx = view.context

                //For use with settings later
                val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

                movieNameTV.text = movieResult.title
                movieYearTV.text = movieResult.year

                /*
                 * Load movie poster into ImageView using Glide: https://bumptech.github.io/glide/
                 */
                val posterStr = movieResult.poster
                Glide.with(ctx)
                    .load(posterStr)
                    .into(moviePosterIV)

                 */
                var startYear: Int
                var endYear: Int
                val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
                try {
                    startYear =
                        prefs.getString(getString(R.string.pref_start_year_key), "1000")!!.toInt()
                }
                catch (e: java.lang.NumberFormatException) {
                    startYear = 1000
                    Toast.makeText(requireContext(), "Invalid Start Year", Toast.LENGTH_SHORT).show()
                }
                try {
                    endYear =
                        prefs.getString(getString(R.string.pref_end_year_key), "2025")!!.toInt()
                }
                catch (e: java.lang.NumberFormatException) {
                    Toast.makeText(requireContext(), "Invalid End Year", Toast.LENGTH_SHORT).show()
                    endYear = 2025
                }

                if (startYear > 2025 || startYear < 0){
                    startYear = 1000
                    Toast.makeText(requireContext(), "Invalid Start Year", Toast.LENGTH_SHORT).show()
                }

                if (endYear < 1888){
                    Toast.makeText(requireContext(), "Invalid End Year", Toast.LENGTH_SHORT).show()
                    endYear = 2025
                }

                if (endYear < startYear){
                    Toast.makeText(requireContext(), "Invalid Years", Toast.LENGTH_SHORT).show()
                    endYear = 2025
                    startYear = 1000
                }


                val mutableMovies = searchResult.movies.toMutableList()
                for (movie in searchResult.movies){

                    if ((movie.year.take(4).toInt() > endYear) || (movie.year.take(4).toInt() < startYear)) {
                        mutableMovies.remove(movie)
                    }


                }

                movieAdapter.updateMovieList(mutableMovies)
                //For use with settings later



                movieListRV.visibility = View.VISIBLE
                movieListRV.scrollToPosition(0)

            }
        }

        /*
         * Set up an observer on the error associated with the current API call.  If the error is
         * not null, display the error that occurred in the UI.
         */
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                loadingErrorTV.text = getString(R.string.loading_error, error.message)
                loadingErrorTV.visibility = View.VISIBLE
                Log.e(tag, "Error fetching forecast: ${error.message}")
            }
        }

        /*
         * Set up an observer on the loading status of the API query.  Display the correct UI
         * elements based on the current loading status.
         */
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                loadingIndicator.visibility = View.VISIBLE
                loadingErrorTV.visibility = View.INVISIBLE
                movieListRV.visibility = View.INVISIBLE
            } else {
                loadingIndicator.visibility = View.INVISIBLE
            }
        }

        searchButton.setOnClickListener {
            val query = searchBoxET.text.toString()
            //ADD YEAR SUPPORT FROM SETTINGS?
            //  MAYBE MOVIE VS. TV SEARCH TOO?
            //val sort = prefs.getString(getString(R.string.pref_year_key), null)
            if (!TextUtils.isEmpty(query)) {
                viewModel.loadSearchResults(
                    apiKey = "1d310886",
                    title = query,
                    year = null
                )
            }
        }
    }

}