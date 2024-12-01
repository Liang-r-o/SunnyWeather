package com.example.sunnyweatheer.logic.network

import android.util.Log
import com.example.sunnyweatheer.logic.network.SunnyWeatherNetwork.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.http.Query
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {
    //当你调用 retrofit.create(PlaceService::class.java) 时，
    // Retrofit 会使用 Java 动态代理机制创建一个实现了 PlaceService 接口的代理对象。
    private val placeService = ServiceCreator.create(PlaceService::class.java)
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    //suspend 关键字表明 searchPlaces 是一个挂起函数。它可以在协程中调用，
    //并且可以在等待 placeService.searchPlaces(query).await() 返回结果时暂停执行。
    suspend fun getDailyWeather(lng:String,lat:String) = weatherService.getDailyWeather(lng,lat).await()

    suspend fun getRealtimeWeather(lng:String,lat:String) = weatherService.getRealtimeWeather(lng,lat).await()

    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation -> enqueue(object:Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()

                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(
                    RuntimeException("response body is null")
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })  }
    }
}