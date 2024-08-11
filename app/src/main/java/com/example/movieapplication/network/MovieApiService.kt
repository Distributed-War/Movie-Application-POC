package com.example.movieapplication.network

import com.example.movieapplication.network.data.Movie
import com.example.movieapplication.network.data.ResponseMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MovieApiService {
    @GET("/3/trending/movie/day")
    suspend fun getMovies(
        @Header("Authorization") gToken: String?,
    ): com.example.movieapplication.network.data.ResponseMovie
}