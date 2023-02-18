package com.example.movelsys.domain_layer.use_cases

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.example.movelsys.data_layer.google_fit.GoogleFitPermissions
import com.example.movelsys.data_layer.google_fit.fetchSensorData.GoogleSensorData
import com.example.movelsys.data_layer.google_fit.Responses
import com.example.movelsys.data_layer.google_fit.fetchHistoryData.GoogleFetchData
import java.util.*

class GoogleFetchUseCase(
    private val googleFetchData: GoogleFetchData,
    private val googleSensorData: GoogleSensorData
) {
    fun fetchPastMonthSteps(responses: Responses) {
        googleFetchData.fetchPastMonthStepCount(responses)
    }

    fun subscribeToStepsListener() {
        googleFetchData.subscribeToStepsListener()
        googleSensorData.addRawDataListener()
    }

    fun getNecessaryParameters(activity: Activity, context: Context) {
        googleFetchData.getActivityAndContext(activity, context)
        googleSensorData.getActivityAndContext(activity, context)
    }

    fun getDataPoints() = googleFetchData.getDataPointList()
    fun isUserSubscribed() = googleFetchData.isUserSubscribedToStepsListener

    fun fetchCurrentSteps(): Int {
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

    fun fetchWeeklySteps(): Int {
        return googleSensorData.getWeeklySteps()
    }

    fun fetchMonthlySteps(): Int {
        return googleSensorData.getMonthlySteps()
    }

    fun detectGivenPermissions(activity: Activity, context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GoogleFitPermissions(
                appContext = context,
                activity = activity
            ).detectIfPermissionIsGiven()
        }
    }

}