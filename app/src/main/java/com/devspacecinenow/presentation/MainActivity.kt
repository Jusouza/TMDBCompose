package com.devspacecinenow.presentation

import android.R.attr.label
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devspacecinenow.R
import com.devspacecinenow.data.api.ApiService
import com.devspacecinenow.data.api.RetrofitClient
import com.devspacecinenow.data.model.MovieDto
import com.devspacecinenow.data.model.MovieResponse
import com.devspacecinenow.presentation.theme.CineNowTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CineNowTheme {
                var nowPlayingMovies by remember { mutableStateOf<List<MovieDto>>(emptyList()) }
                var topRatedMovies by remember { mutableStateOf<List<MovieDto>>(emptyList()) }
                var upcomingMovies by remember { mutableStateOf<List<MovieDto>>(emptyList()) }
                var popularMovies by remember { mutableStateOf<List<MovieDto>>(emptyList()) }

                val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
                val callNowPlaying = apiService.getNowPlayingMovies()
                val callUpcoming = apiService.getUpcomingMovies()
                val callPopular = apiService.getPopularMovies()
                val callTopRated = apiService.getTopRatedMovies()

                callNowPlaying.enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(
                        call: Call<MovieResponse?>,
                        response: Response<MovieResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results
                            if (movies != null) {
                                nowPlayingMovies = movies
                            }
                        } else {
                            Log.d("MainActivity", "Request Error:: ${response.errorBody()}")

                        }
                    }

                    override fun onFailure(
                        call: Call<MovieResponse?>,
                        t: Throwable
                    ) {
                        Log.d("MainActivity", "Networking Error:: ${t.message}")
                    }
                })

                callUpcoming.enqueue(object : Callback<MovieResponse> {
                        override fun onResponse(
                            call: Call<MovieResponse?>,
                            response: Response<MovieResponse?>
                        ) {
                            if (response.isSuccessful) {
                                val movies = response.body()?.results
                                if (movies != null) {
                                    upcomingMovies = movies
                                }
                            } else {
                                Log.d("MainActivity", "Request Error:: ${response.errorBody()}")

                            }
                        }

                        override fun onFailure(
                            call: Call<MovieResponse?>,
                            t: Throwable
                        ) {
                            Log.d("MainActivity", "Networking Error:: ${t.message}")
                        }
                    })

                callPopular.enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(
                        call: Call<MovieResponse?>,
                        response: Response<MovieResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results
                            if (movies != null) {
                                popularMovies = movies
                            }
                        } else {
                            Log.d("MainActivity", "Request Error:: ${response.errorBody()}")

                        }
                    }

                    override fun onFailure(
                        call: Call<MovieResponse?>,
                        t: Throwable
                    ) {
                        Log.d("MainActivity", "Networking Error:: ${t.message}")
                    }
                })

                callTopRated.enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(
                        call: Call<MovieResponse?>,
                        response: Response<MovieResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results
                            if (movies != null) {
                                topRatedMovies = movies
                            }
                        } else {
                            Log.d("MainActivity", "Request Error:: ${response.errorBody()}")

                        }
                    }

                    override fun onFailure(
                        call: Call<MovieResponse?>,
                        t: Throwable
                    ) {
                        Log.d("MainActivity", "Networking Error:: ${t.message}")
                    }
                })

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   Column (
                       modifier = Modifier
                           .fillMaxSize()
                           .verticalScroll(rememberScrollState())
                   ){
                        Text(
                            modifier = Modifier.padding(8.dp),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.SemiBold,
                            text = getString(R.string.app_name)
                        )

                    MovieSession(
                        label = getString(R.string.top_rated_movies_title),
                        movieList = topRatedMovies,
                        onClick = { movieClicked ->
                    }
                    )
                       MovieSession(
                        label = getString(R.string.now_playing_movies_title),
                        movieList = nowPlayingMovies,
                        onClick = { movieClicked ->
                    }
                    )
                       MovieSession(
                           label = getString(R.string.upcoming_movies_title),
                           movieList = upcomingMovies,
                           onClick = { movieClicked ->
                           }
                       )
                       MovieSession(
                           label = getString(R.string.popular_movies_title),
                           movieList = popularMovies,
                           onClick = { movieClicked ->
                           }
                       )
                   }
                }
            }
        }
    }
}

@Composable
fun MovieSession(
    label: String,
    movieList: List<MovieDto>,
    onClick: (MovieDto) -> Unit
){
      Column (
          modifier = Modifier
              .fillMaxWidth()
              .padding(8.dp)
      ) {
          Text(
              fontSize = 24.sp,
              text = label,
              fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.size(8.dp))
          MovieList(movieList = movieList, onClick = onClick)
      }
}

@Composable
fun MovieList(
    movieList: List<MovieDto>,
    onClick: (MovieDto) -> Unit
) {
    LazyRow {
        items(movieList) {
            MovieItem(
                movieDto = it,
                onClick = onClick
            )
        }
    }
}

@Composable
fun MovieItem(
    movieDto: MovieDto,
    onClick: (MovieDto) -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable {
            onClick.invoke(movieDto)
        }
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(end = 6.dp)
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieDto.posterFullPath,
            contentDescription = "${movieDto.title} Poster image",
            placeholder = painterResource(R.drawable.ic_place_holder),
            error = painterResource(R.drawable.ic_place_holder)
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            modifier = Modifier
                .padding(end = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            text = movieDto.title
        )
        Text(
            modifier = Modifier
                .padding(end = 4.dp),
            maxLines = 1,
            text = movieDto.overview,
            overflow = TextOverflow.Ellipsis,
        )
    }
}