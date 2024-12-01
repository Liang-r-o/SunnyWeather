package com.example.sunnyweatheer.logic.model

// 封装Realtime和dailyResponse
data class Weather(val realtime: RealTimeResponse.Realtime,val daily: DailyRespons.Daily)