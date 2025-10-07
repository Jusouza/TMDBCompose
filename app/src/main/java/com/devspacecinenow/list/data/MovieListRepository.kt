package com.devspacecinenow.list.data

import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.local.MovieListLocalDataSource
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource

class MovieListRepository(
    private val localDS: MovieListLocalDataSource,
    private val remoteDS: MovieListRemoteDataSource
) {

    suspend fun getNowPlaying(): Result<List<Movie>?> {
        return try {
            val result = remoteDS.getNowPlaying()
            if (result.isSuccess){
            val moviesRemote = result.getOrNull() ?: emptyList()
                if(moviesRemote.isNotEmpty()){
                    localDS.updateLocalItems(moviesRemote)
                }
            } else {
                val localData = localDS.getNowPlayingMovies()
                if(localData.isEmpty()){
                    return@getNowPlaying result
                }
            }
            Result.success(localDS.getNowPlayingMovies())

        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getTopRated(): Result<List<Movie>?> {
        return try {
            val result = remoteDS.getTopRated()
            if (result.isSuccess){
                val moviesRemote = result.getOrNull() ?: emptyList()
                if(moviesRemote.isNotEmpty()){
                    localDS.updateLocalItems(moviesRemote)
                }
            } else {
                val localData = localDS.getTopRatedMovies()
                if(localData.isEmpty()){
                    return@getTopRated result
                }
            }
            Result.success(localDS.getTopRatedMovies())

        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getUpcoming(): Result<List<Movie>?> {
        return try {
            val result = remoteDS.getUpcoming()
            if (result.isSuccess){
                val moviesRemote = result.getOrNull() ?: emptyList()
                if(moviesRemote.isNotEmpty()){
                    localDS.updateLocalItems(moviesRemote)
                }
            } else {
                val localData = localDS.getUpcomingMovies()
                if(localData.isEmpty()){
                    return@getUpcoming result
                }
            }
            Result.success(localDS.getUpcomingMovies())

        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getPopular(): Result<List<Movie>?> {
        return try {
            val result = remoteDS.getPopular()
            if (result.isSuccess){
                val moviesRemote = result.getOrNull() ?: emptyList()
                if(moviesRemote.isNotEmpty()){
                    localDS.updateLocalItems(moviesRemote)
                }
            } else {
                val localData = localDS.getPopularMovies()
                if(localData.isEmpty()){
                    return@getPopular result
                }
            }
            Result.success(localDS.getPopularMovies())

        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}