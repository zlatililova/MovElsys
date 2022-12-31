package com.example.movelsys.data_layer.google_fit

import android.app.Activity
import android.content.Context
import com.google.android.gms.fitness.data.DataPoint

interface GoogleFetchData {
    fun subscribeToStepsListener()
    fun listActiveSubscriptions()
    fun fetchPastWeekStepCount(responses: Responses)
    fun getActivityandContext(activity: Activity, context: Context)
    fun getDataPointList(): List<DataPoint>
}