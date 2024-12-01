package com.example.sunnyweatheer.logic.network

import android.telecom.Call
import com.example.sunnyweatheer.SunnyWeatherApplication
import com.example.sunnyweatheer.logic.model.DailyRespons
import com.example.sunnyweatheer.logic.model.RealTimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng:String,@Path("lat")lat:String)
    :retrofit2.Call<RealTimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng: String,@Path("lat")lat: String)
    :retrofit2.Call<DailyRespons>


}