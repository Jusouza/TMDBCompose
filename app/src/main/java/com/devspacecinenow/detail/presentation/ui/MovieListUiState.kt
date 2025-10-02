package com.devspacecinenow.detail.presentation.ui

data class MovieListUiState(
    val list: List<MovieListUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)

data class MovieListUiData(
    val id: Int,
    val title: String,
    val overview: String,
    val image: String
)