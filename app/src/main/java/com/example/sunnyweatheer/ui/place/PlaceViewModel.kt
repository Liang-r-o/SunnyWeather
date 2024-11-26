package com.example.sunnyweatheer.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.sunnyweatheer.logic.Repository
import com.example.sunnyweatheer.logic.model.PlaceRespnse
import retrofit2.http.Query
import javax.xml.transform.Transformer

class PlaceViewModel:ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    // 对于界面上显示的城市进行缓存
    val placeList = ArrayList<PlaceRespnse.Place>()

    val placeLiveData = searchLiveData.switchMap { query ->
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query: String){
        searchLiveData.value = query
    }
}