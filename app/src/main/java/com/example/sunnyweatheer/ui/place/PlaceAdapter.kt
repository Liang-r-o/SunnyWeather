package com.example.sunnyweatheer.ui.place

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweatheer.R
import com.example.sunnyweatheer.logic.model.PlaceRespnse
import com.example.sunnyweatheer.ui.weather.WeatherActivity
import kotlinx.coroutines.NonDisposableHandle.parent

class PlaceAdapter(private val fragment:Fragment,private val placeList:List<PlaceRespnse.Place>):
RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){




    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val placeName:TextView = view.findViewById(R.id.placeName)
        val placeAddress:TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address

        holder.itemView.setOnClickListener {
          //  val position = holder.bindingAdapterPosition
            Log.d("RecyclerView", "holder: $holder")

            Log.d("RecyclerView", "Clicked position: $position, list size: ${placeList.size}")

            val place = placeList[position]
            val intent = Intent(holder.itemView.context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            fragment.startActivity(intent)
        }

    }
    override fun getItemCount()=placeList.size
}