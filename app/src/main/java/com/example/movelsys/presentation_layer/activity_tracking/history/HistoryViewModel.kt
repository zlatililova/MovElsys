package com.example.movelsys.presentation_layer.activity_tracking.history

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movelsys.data_layer.google_fit.GoogleFitPermissions
import com.example.movelsys.data_layer.google_fit.Responses
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryViewModel(
    private val googleFetchUseCase: GoogleFetchUseCase
) : ViewModel() {

    var timesDataWasFetched: Int by mutableStateOf(0)
    private var googleFitHistory: Map<String, Int> by mutableStateOf(mapOf())
    var googleFitDates: List<String> by mutableStateOf(listOf())
    var googleFitSteps: List<Int> by mutableStateOf(listOf())

    fun getActivityAndContext(activity: Activity, context: Context) {
        googleFetchUseCase.getNecessaryParameters(activity, context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GoogleFitPermissions(
                appContext = context,
                activity = activity
            ).detectIfPermissionIsGiven()
        }
        if (timesDataWasFetched == 0) {
            startGoogleFit()
        }
    }

    fun startGoogleFit() {
        googleFetchUseCase.fetchPastMonthSteps(responses = object : Responses {
            override fun onSuccess() {
                Log.i(TAG, "OnSuccess - viewModel")
            }

            override fun onError(error: String) {
                Log.i(TAG, "OnError")
            }
        })
        Handler().postDelayed({
            fetchGoogleData()
        }, 1500)
        timesDataWasFetched += 1
    }

    private fun fetchGoogleData() {
        val gson = Gson()
        googleFitHistory = gson.fromJson(
            googleFetchUseCase.getDataPoints(),
            object : TypeToken<Map<String, Int>>() {}.type
        )
        googleFitDates = googleFitHistory.keys.toList()
        googleFitDates = googleFitDates.reversed()
        googleFitSteps = googleFitHistory.values.toList()
        googleFitSteps = googleFitSteps.reversed()
    }
}