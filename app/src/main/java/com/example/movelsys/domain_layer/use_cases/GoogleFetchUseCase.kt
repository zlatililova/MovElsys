package com.example.movelsys.domain_layer.use_cases

import android.app.Activity
import android.content.Context
import com.example.movelsys.data_layer.google_fit.fetchHistoryData.GoogleFetchData
import com.example.movelsys.data_layer.google_fit.Responses

class GoogleFetchUseCase(private val googleFetchData: GoogleFetchData) {
    fun startBusinessLogic(responses: Responses) {
        googleFetchData.fetchPastWeekStepCount(responses)
    }

    fun subscribeToStepsListener() {
        googleFetchData.subscribeToStepsListener()
    }

    fun getNecessaryParameters(activity: Activity, context: Context) {
        googleFetchData.getActivityAndContext(activity, context)
    }

    fun getDataPoints() = googleFetchData.getDataPointList()
    fun isUserSubscribed() = googleFetchData.isUserSubscribedToStepsListener()
}