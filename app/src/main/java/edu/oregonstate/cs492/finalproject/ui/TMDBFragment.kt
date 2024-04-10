package edu.oregonstate.cs492.finalproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.oregonstate.cs492.finalproject.R
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieItem
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TMDBFragment : Fragment(R.layout.fragment_detail){
    private val tag = "MovieDetail"
    private val viewModel: TMDBMovieViewModel by viewModels()
    private val movieAdapter = TMDBMovieAdapter(::onMovieClick)
    private val service = TMDBMovieService.create()
    private lateinit var testing: TextView

    private lateinit var movieListRV: RecyclerView





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(tag, "Entered $tag")

        testing = view.findViewById(R.id.hellotest)
        movieListRV = view.findViewById(R.id.rv_now_showing)

        movieListRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        movieListRV.setHasFixedSize(true)
        movieListRV.adapter = movieAdapter

        println("Unloaded")
        viewModel.loadTMDBMovie("0aa19f793ac2816d2230864c049e6f0e")
        println("Loaded")

        viewModel.movie.observe(viewLifecycleOwner){ movie ->

            println("HELHRLK ${movie?.results}")
            movieAdapter.updateMovie(movie)
            movieListRV.visibility = View.VISIBLE
            movieListRV.scrollToPosition(0)

        }

//        posterIV.setOnClickListener {
//
//
//            val snackbar = Snackbar.make(it, "Hello", Snackbar.LENGTH_INDEFINITE)
//            snackbar.setAction("X") {
//                snackbar.dismiss()
//            }
//            snackbar.show()
//
//            true
//
//        }


//        service.loadTMDBMovie("0aa19f793ac2816d2230864c049e6f0e").enqueue(object : retrofit2.Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                Log.d("MainActivity", "Status code:${response.code()}")
//                Log.d("MainActivity", "Body:${response.body()}")
//
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.d("MainActivity", "ERROR :${t.message}")
//
//            }
//
//        })





    }

    override fun onResume() {
        super.onResume()

//        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
//        val city = prefs.getString(getString(R.string.pref_city_key), "Corvallis,OR,US")
//        val units = prefs.getString(
//            getString(R.string.pref_units_key),
//            getString(R.string.pref_units_default_value)
//        )
        viewModel.loadTMDBMovie("0aa19f793ac2816d2230864c049e6f0e")
        println("RESUME")
    }

    private fun onMovieClick(movie: TMDBMovieItem){
        Log.d("TMDBFragment",movie.title)
        val directions = TMDBFragmentDirections.navigateToMovieDetail(movie)
        findNavController().navigate(directions)
    }
}