package com.example.movieapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.movieapplication.network.data.Movie

class MovieDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieTitle = intent.getStringExtra(EXTRA_TITLE) ?: ""
        val movieDescription = intent.getStringExtra(EXTRA_DESCRIPTION) ?: ""
        val moviePosterUrl = intent.getStringExtra(EXTRA_POSTER_URL) ?: ""

        setContent {
            MovieDetailScreen(LocalContext.current,movieTitle, movieDescription, moviePosterUrl)
        }
    }

    companion object {
        private const val EXTRA_TITLE = "movie_title"
        private const val EXTRA_DESCRIPTION = "movie_description"
        private const val EXTRA_POSTER_URL = "movie_poster_url"

        fun newIntent(context: Context, movie: Movie): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_TITLE, movie.title)
                putExtra(EXTRA_DESCRIPTION, movie.description)
                putExtra(EXTRA_POSTER_URL, movie.posterPath)
            }
        }
    }
}

@Composable
fun MovieDetailScreen(context: Context,title: String, description: String, posterUrl: String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val imageUrl = "https://image.tmdb.org/t/p/w500${posterUrl}"
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .wrapContentWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(10.dp))
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = description, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
