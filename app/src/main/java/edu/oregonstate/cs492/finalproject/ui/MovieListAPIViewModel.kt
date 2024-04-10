package edu.oregonstate.cs492.finalproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.finalproject.data.MovieAPIService
import edu.oregonstate.cs492.finalproject.data.MovieListAPIResponse
import edu.oregonstate.cs492.finalproject.data.MovieListAPIResponseRepository
import kotlinx.coroutines.launch

class MovieListAPIViewModel : ViewModel() {
    private val repository =  MovieListAPIResponseRepository(MovieAPIService.create())

    private val _searchResults = MutableLiveData<MovieListAPIResponse?>(null)
    val searchResults: LiveData<MovieListAPIResponse?> = _searchResults

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?> = _error

    fun loadSearchResults(apiKey: String, title: String?, year: String?) {
        viewModelScope.launch {
            //Set to Loading while search is occurring
            _loading.value = true
            val result = repository.loadMovieListAPIResult(apiKey, title, year)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _searchResults.value = result.getOrNull()
        }
    }
}