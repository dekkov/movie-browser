package edu.oregonstate.cs492.finalproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.oregonstate.cs492.finalproject.R
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovie
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieItem

class TMDBMovieAdapter(private val onMovieClick: (TMDBMovieItem) -> Unit) : RecyclerView.Adapter<TMDBMovieAdapter.ViewHolder>() {
    var Movies: List<TMDBMovieItem> = listOf()

    fun updateMovie(movie: TMDBMovie?) {
        notifyItemRangeRemoved(0, Movies.size)
        Movies = movie?.results ?: listOf()
        notifyItemRangeInserted(0, Movies.size)
    }
    override fun getItemCount() = Movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view, onMovieClick)
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.bind(Movies[position])
    }

    class ViewHolder(itemView: View, onClick: (TMDBMovieItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val posterIV: ImageView = itemView.findViewById(R.id.poster)

        private lateinit var currentMovie: TMDBMovieItem
        init {


            posterIV.setOnClickListener{
                currentMovie.let(onClick)

            }
        }



        fun bind(movie: TMDBMovieItem) {
            currentMovie = movie

            val ctx = itemView.context


            /*
             * Load poster into ImageView using Glide: https://bumptech.github.io/glide/
             */
            Glide.with(ctx)
                .load(movie.poster_path)
                .into(posterIV)

        }



    }
}