package com.example.movelsys.domain_layer.use_cases

import android.app.Activity
import android.content.Context
import com.example.movelsys.data_layer.google_fit.GoogleFetchData
import com.example.movelsys.data_layer.google_fit.Responses
import com.google.android.gms.fitness.data.DataPoint

class GoogleFetchUseCase(private val googleFetchData: GoogleFetchData) {
    fun startBusinessLogic(responses: Responses){
        googleFetchData.subscribeToStepsListener()
        googleFetchData.fetchPastWeekStepCount(responses)
    }
    fun getNecessaryParameters(activity: Activity, context: Context){
        googleFetchData.getActivityandContext(activity, context)
    }

    fun getDataPoints() = googleFetchData.getDataPointList()
}