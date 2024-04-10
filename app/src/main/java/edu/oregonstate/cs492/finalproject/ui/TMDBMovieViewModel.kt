package edu.oregonstate.cs492.finalproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovie
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieRepository
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieService
import kotlinx.coroutines.launch

class TMDBMovieViewModel: ViewModel() {
    private val repository = TMDBMovieRepository(TMDBMovieService.create())

    private val _movie = MutableLiveData<TMDBMovie?>(null)

    val movie: LiveData<TMDBMovie?> = _movie

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)

    val loading: LiveData<Boolean> = _loading

    fun loadTMDBMovie(apiKey: String) {
        /*
         * Launch a new coroutine in which to execute the API call.  The coroutine is tied to the
         * lifecycle of this ViewModel by using `viewModelScope`.
         */
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadTMDBMovie(apiKey)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _movie.value = result.getOrNull()
        }
    }
}