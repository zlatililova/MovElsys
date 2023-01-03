package com.example.movelsys.data_layer.google_fit

import android.app.Activity
import android.content.Context
import com.google.android.gms.fitness.data.DataPoint

interface GoogleFetchData {
    fun subscribeToStepsListener()
    fun isUserSubscribedToStepsListener(): Boolean
    fun fetchPastWeekStepCount(responses: Responses)
    fun getActivityandContext(activity: Activity, context: Context)
    fun getDataPointList(): String
    fun checkIfFetchIsFinished(): Boolean
}