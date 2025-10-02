package com.devspacecinenow.detail.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.R
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.detail.presentation.MovieDetailViewModel
import com.devspacecinenow.presentation.theme.CineNowTheme

@Composable
fun MovieDetailScreen(
    movieId: String,
    navController: NavHostController,
    viewModel: MovieDetailViewModel
) {
    val movieDto by viewModel.uiMovieDetails.collectAsState()
    viewModel.fetchDetailMovies(movieId)

    movieDto?.let {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    viewModel.cleanMovieId()
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_content_description)
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = it.title,
                    fontSize = 16.sp
                )
            }
            MovieDetailContent(it)
        }
    }
}


@Composable
private fun MovieDetailContent(movieData: MovieDto) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            model = movieData.posterFullPath,
            contentDescription = "${movieData.title} Poster image"
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = movieData.overview,
            fontSize = 16.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    CineNowTheme {
        val movie = MovieDto(
            id = 9,
            title = "Title",
            posterPath = "juliana",
            overview = "Este texto é relativamente grande apenas para fazer um teste do comportamento de como esta o texto no preview. Este texto é relativamente grande apenas para fazer um teste do comportamento de como esta o texto no preview. Este texto é relativamente grande apenas para fazer um teste do comportamento de como esta o texto no preview"
        )
        MovieDetailContent(movieData = movie)
    }

}