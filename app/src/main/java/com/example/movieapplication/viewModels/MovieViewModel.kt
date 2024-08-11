package com.example.movieapplication.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.Constants
import com.example.movieapplication.network.MovieApiService
import com.example.movieapplication.network.data.Movie
import com.example.movieapplication.network.data.ResponseMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    val movies: LiveData<List<Movie>> = _movies

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _filteredMovies = MutableLiveData<List<Movie>>(emptyList())
    val filteredMovies: LiveData<List<Movie>> = _filteredMovies

    var loading = MutableLiveData(false)

    init {
        fetchMovies()
        viewModelScope.launch {
            movies.observeForever { updateFilteredMovies() }
            searchQuery.observeForever { updateFilteredMovies() }
        }
    }

    private fun fetchMovies() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val api = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MovieApiService::class.java)

                val call = api.getMovies(Constants.AUTORIZATION_KEY)
                withContext(Dispatchers.Main) {
                    _movies.value = call.results
                    loading.value = false
                }
            } catch(exception: Exception) {
                Log.i("api Fetch Error",exception.toString())
            }
        }

    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun updateFilteredMovies() {
        val query = _searchQuery.value ?: ""
        val moviesList = _movies.value ?: emptyList()
        _filteredMovies.value = if (query.isBlank()) {
            moviesList
        } else {
            moviesList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }
}
