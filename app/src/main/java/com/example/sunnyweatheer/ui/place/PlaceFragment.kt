package com.example.sunnyweatheer.ui.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweatheer.R

class PlaceFragment:Fragment() {
    private val TAG = "PlaceFragment"
//这行代码使用 lazy 初始化来延迟创建 ViewModel,会在第一次访问 viewModel 属性时执行。
private val viewModel by lazy { ViewModelProvider.create(this).get(PlaceViewModel::class.java) }
    //private val viewModel: PlaceViewModel by viewModels()
    private lateinit var adapter: PlaceAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    @SuppressLint("NotifyDataSetChanged")
    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onActivityCreated(savedInstanceState)",
            "androidx.fragment.app.Fragment"
        )
    )


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        if (recyclerView != null) {
            recyclerView.layoutManager = layoutManager
        }
        adapter = PlaceAdapter(this,viewModel.placeList)
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }
        val searchPlaceEdit = view?.findViewById<EditText>(R.id.searchPlaceEdit)
        searchPlaceEdit?.addTextChangedListener { editable ->
            val content = editable.toString()
            //Log.d(TAG,content)
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                recyclerView?.visibility = View.GONE
                view?.findViewById<ImageView>(R.id.bgImageView)?.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            Log.d(TAG, places.toString())
            if (places != null){
                recyclerView?.visibility = View.VISIBLE
                view?.findViewById<ImageView>(R.id.bgImageView)?.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()

            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        /*
        searchPlaceEdit?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        })

         */

    }
}