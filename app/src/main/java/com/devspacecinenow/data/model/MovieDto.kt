package com.devspacecinenow.data.model

import kotlinx.serialization.SerialName


data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val postPath: String
)