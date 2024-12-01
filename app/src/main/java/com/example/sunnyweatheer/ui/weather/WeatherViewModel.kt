package com.example.sunnyweatheer.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.sunnyweatheer.logic.Repository
import com.example.sunnyweatheer.logic.model.PlaceRespnse

class WeatherViewModel:ViewModel() {
    private val locationLiveData = MutableLiveData<PlaceRespnse.Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val wetherLiveData = locationLiveData.switchMap { location ->
        Repository.refreshWeather(location.lng,location.lat)

    }

    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value = PlaceRespnse.Location(lng, lat)
    }
}