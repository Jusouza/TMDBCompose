package com.devspacecinenow

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspacecinenow.detail.presentation.MovieDetailViewModel
import com.devspacecinenow.detail.presentation.ui.MovieDetailScreen
import com.devspacecinenow.list.presentation.MovieListViewModel
import com.devspacecinenow.list.presentation.ui.MovieListScreen

@Composable
fun CineNowApp(
    listViewModel: MovieListViewModel,
    detailViewModel: MovieDetailViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MOVIE_LIST_ROUTE){
        composable(route = MOVIE_LIST_ROUTE){
            MovieListScreen(navController, listViewModel)
        }
        composable(
            route = MOVIE_DETAIL_ROUTE + MOVIE_DETAIL_ROUTE_ID,
            arguments = listOf(navArgument(MOVIE_DETAIL_ROUTE_ARG){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getString(MOVIE_DETAIL_ROUTE_ARG))
            MovieDetailScreen(movieId, navController, detailViewModel)
        }
    }
}

const val MOVIE_LIST_ROUTE = "movieList"
const val MOVIE_DETAIL_ROUTE = "movieDetail"
const val MOVIE_DETAIL_ROUTE_ID = "/{itemId}"
const val MOVIE_DETAIL_ROUTE_ARG = "itemId"