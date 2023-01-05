package com.example.movelsys.domain_layer.use_cases

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.movelsys.data_layer.google_fit.fetchSensorData.GoogleSensorData
import com.example.movelsys.data_layer.google_fit.Responses
import com.example.movelsys.data_layer.google_fit.fetchHistoryData.GoogleFetchData
import java.util.*

class GoogleFetchUseCase(
    private val googleFetchData: GoogleFetchData,
    private val googleSensorData: GoogleSensorData
) {
    fun fetchPastMonthSteps(responses: Responses) {
        googleFetchData.fetchPastWeekStepCount(responses)
    }

    fun subscribeToStepsListener() {
        googleFetchData.subscribeToStepsListener()
    }

    fun getNecessaryParameters(activity: Activity, context: Context) {
        googleFetchData.getActivityAndContext(activity, context)
        googleSensorData.getActivityAndContext(activity, context)
    }

    fun getDataPoints() = googleFetchData.getDataPointList()
    fun isUserSubscribed() = googleFetchData.isUserSubscribedToStepsListener()
    fun fetchCurrentSteps(): Int {
        Log.e("Daily steps in UC", googleSensorData.getDailySteps().toString())
        val timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    googleSensorData.getDailySteps()
                }
            }, 0, 1000
        )
        return googleSensorData.getCurrentSteps()
    }

    fun getDefaultStepGoal() = googleSensorData.getDefaultStepGoal()
    fun setDefaultStepGoal(newGoal: Int) = googleSensorData.setDefaultStepGoal(newGoal)
}