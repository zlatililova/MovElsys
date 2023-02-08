package com.example.movelsys.data_layer.google_fit.fetchSensorData

import android.app.Activity
import android.content.Context

interface GoogleSensorData {
    fun getActivityAndContext(activity: Activity, context: Context)
    fun addRawDataListener()
    fun getDailySteps()
    fun getCurrentSteps(): Int
    fun getWeeklySteps(): Int
    fun getMonthlySteps(): Int
}