package com.example.sunnyweatheer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.media.session.MediaSession.Token

class SunnyWeatherApplication :Application(){
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "X5sXnwwXQbFbMfG6"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}