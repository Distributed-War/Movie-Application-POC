package com.example.movieapplication.network.data

import com.google.gson.annotations.SerializedName

data class ResponseMovie(
    @SerializedName("results") val results: List<Movie>
)
