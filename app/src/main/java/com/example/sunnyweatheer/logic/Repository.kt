package com.example.sunnyweatheer.logic

 import android.health.connect.datatypes.RestingHeartRateRecord
 import android.provider.ContactsContract.CommonDataKinds.StructuredName
 import android.util.Log
 import androidx.lifecycle.liveData
 import com.example.sunnyweatheer.logic.model.PlaceRespnse
 import com.example.sunnyweatheer.logic.model.Weather
 import com.example.sunnyweatheer.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
 import kotlinx.coroutines.async
 import kotlinx.coroutines.coroutineScope
 import okhttp3.Dispatcher
 import kotlin.Exception

object Repository {

    private const val TAG = "Repository"
    fun searchPlaces(query:String) = liveData<Result<List<PlaceRespnse.Place>>>(Dispatchers.IO) {
        val result = try {
            val placesResponse = SunnyWeatherNetwork.searchPlaces(query)
            Log.d(TAG,placesResponse.status)
            if (placesResponse.status == "ok"){
                val places = placesResponse.places
                Result.success(places)
              //  emit(placesResponse.places)
            }else{
                Result.failure(RuntimeException("response status is ${placesResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }

        emit(result)
    }
//Dispatchers.IO 指定了 liveData 内的代码将在 I/O 线程池中执行。这意味着网络请求和其他 I/O 操作不会阻塞主线程。
    fun refreshWeather(lng:String,lat:String) = liveData<Result<Weather>>(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
                }

                val realTimeResponse = deferredRealtime.await()
                val dailyRespons = deferredDaily.await()

                if (realTimeResponse.status == "ok" && dailyRespons.status == "ok"){
                    val weather = Weather(realTimeResponse.result.realtime,dailyRespons.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure((
                            RuntimeException(
                                "realtime Response status is ${realTimeResponse.status}" +
                                "daily response status is ${dailyRespons.status}"
                            )
                            ))
                }
            }
        }catch (e:Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }
}