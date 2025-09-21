package com.example.wheatherapp.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.weatherapp.api.CitySuggestion
import com.example.wheatherapp.weatherapp.api.NetworkResponse
import com.example.wheatherapp.weatherapp.api.RetrofitInstance
import com.example.wheatherapp.weatherapp.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val API_KEY = BuildConfig.API_KEY
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    private val _suggestions = MutableLiveData<NetworkResponse<List<CitySuggestion>>>()
    val suggestions: LiveData<NetworkResponse<List<CitySuggestion>>> = _suggestions

    // Fetch weather data for the selected city
    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(API_KEY, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

    // Fetch city suggestions based on user input
    fun getCitySuggestions(query: String) {
        _suggestions.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getCitySuggestions(API_KEY, query)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _suggestions.value = NetworkResponse.Success(it)
                    } ?: run {
                        _suggestions.value = NetworkResponse.Error("No suggestions found")
                    }
                } else {
                    _suggestions.value = NetworkResponse.Error("Failed to load suggestions")
                }
            } catch (e: Exception) {
                _suggestions.value = NetworkResponse.Error("Failed to load suggestions")
            }
        }
    }
}












