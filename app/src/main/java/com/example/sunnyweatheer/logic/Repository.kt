package com.example.sunnyweatheer.logic

 import android.util.Log
 import androidx.lifecycle.liveData
 import com.example.sunnyweatheer.logic.model.PlaceRespnse
 import com.example.sunnyweatheer.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
 import java.lang.Exception

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
}