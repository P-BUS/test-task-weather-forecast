package com.example.test_task_weather_forecast.data.repository

import android.util.Log
import com.example.test_task_weather_forecast.data.database.WeatherLocalDataSource
import com.example.test_task_weather_forecast.data.model.WeatherForecast
import com.example.test_task_weather_forecast.data.network.ApiResult
import com.example.test_task_weather_forecast.data.network.WetherRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val TAG = "WeatherRepository"

class WeatherRepository @Inject constructor(
    private val network: WetherRemoteDataSource,
    private val database: WeatherLocalDataSource
) {
    suspend fun refreshWeather(cityName: String) {
        withContext(Dispatchers.IO) {
            var weatherList: List<WeatherForecast> = listOf()
            when (val responce = network.getWeatherForecast(cityName)) {
                is ApiResult.Success -> weatherList = responce.data.list
                is ApiResult.Error -> Log.e(TAG, "${responce.code} ${responce.message}")
                is ApiResult.Exception -> Log.e(TAG, "${responce.e.cause} ${responce.e.message}")
            }
        }
    }

}