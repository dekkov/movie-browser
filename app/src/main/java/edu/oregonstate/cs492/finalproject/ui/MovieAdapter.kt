package edu.oregonstate.cs492.finalproject.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.oregonstate.cs492.finalproject.R
import edu.oregonstate.cs492.finalproject.data.LowDetailMovieAPIResponse
import edu.oregonstate.cs492.finalproject.data.MovieAPIResponse
import edu.oregonstate.cs492.finalproject.data.MovieListAPIResponse

class MovieAdapter(
    private val onFavoriteClick: (LowDetailMovieAPIResponse) -> Unit,
    private val onWatchlistClick: (LowDetailMovieAPIResponse) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    var movieResponse: List<LowDetailMovieAPIResponse> = listOf()
    fun updateMovieList(movies: List<LowDetailMovieAPIResponse>){
//    fun updateMovieList(movies: MovieListAPIResponse?){
        notifyItemRangeRemoved(0, movieResponse.size)
        movieResponse = movies
        notifyItemRangeInserted(0, movieResponse.size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder(view, onFavoriteClick, onWatchlistClick)
    }

    override fun getItemCount() = movieResponse.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieResponse[position])
    }

    class ViewHolder(
        itemView: View,
        val onFavoriteClick: (LowDetailMovieAPIResponse) -> Unit,
        val onWatchlistClick: (LowDetailMovieAPIResponse) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val titleTV: TextView = itemView.findViewById(R.id.tv_title)
        private val yearTV: TextView = itemView.findViewById(R.id.tv_year)
        private val imdbButton: Button = itemView.findViewById(R.id.b_imdb)
        private val posterIV: ImageView = itemView.findViewById(R.id.iv_movie_poster)

        private lateinit var currentMovie: LowDetailMovieAPIResponse
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.b_add_to_favorites)
        private val watchlistButton: ImageButton = itemView.findViewById(R.id.b_add_to_watchlist)

        init {
            itemView.setOnLongClickListener{
                println("????????")
                true
            }

            favoriteButton.setOnClickListener {
                onFavoriteClick(currentMovie)
            }

            watchlistButton.setOnClickListener {
                onWatchlistClick(currentMovie)
            }
        }

        fun bind(movie:LowDetailMovieAPIResponse){
            currentMovie = movie

            val ctx = itemView.context
            titleTV.text = movie.title
            yearTV.text = movie.year
            imdbButton.setOnClickListener {
                val url = "http://www.imdb.com/title/"+movie.imdbID
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(ctx, i, null)
            }
            Glide.with(ctx).load(movie.poster).into(posterIV)
        }
    }

}