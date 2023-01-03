package com.example.movelsys.data_layer.google_fit.fetchHistoryData

import android.app.Activity
import android.content.Context
import com.example.movelsys.data_layer.google_fit.Responses

interface GoogleFetchData {
    fun subscribeToStepsListener()
    fun isUserSubscribedToStepsListener(): Boolean
    fun fetchPastWeekStepCount(responses: Responses)
    fun getActivityAndContext(activity: Activity, context: Context)
    fun getDataPointList(): String
}