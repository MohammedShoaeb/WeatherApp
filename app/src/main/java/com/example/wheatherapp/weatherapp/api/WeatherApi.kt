package com.example.wheatherapp.weatherapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("v1/search.json")
    suspend fun getCitySuggestions(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Response<List<CitySuggestion>>

    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): Response<WeatherModel>
}