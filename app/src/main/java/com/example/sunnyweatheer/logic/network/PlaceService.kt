package com.example.sunnyweatheer.logic.network

import com.example.sunnyweatheer.SunnyWeatherApplication
import com.example.sunnyweatheer.logic.model.PlaceRespnse
import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query")query: String):retrofit2.Call<PlaceRespnse.PlaceRespnse>
}