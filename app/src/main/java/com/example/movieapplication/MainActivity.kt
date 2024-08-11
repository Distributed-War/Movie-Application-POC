package com.example.movieapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapplication.viewModels.MovieViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {

    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MovieListScreen(viewModel = movieViewModel)
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}

@Composable
fun MovieListScreen(viewModel: MovieViewModel = viewModel()) {
    val movies by viewModel.filteredMovies.observeAsState(emptyList())
    val searchQuery by viewModel.searchQuery.observeAsState("")
    val isLoading by viewModel.loading.observeAsState(false)
    Box(modifier = Modifier.fillMaxSize()) {
        if(isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                if (searchQuery != null) {
                    SearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChanged = viewModel::onSearchQueryChanged
                    )
                }
                if (movies != null) {
                    MovieGrid(LocalContext.current, movies = movies)
                }
            }
        }
    }
}


