package com.example.movelsys.presentation_layer.activity_tracking.life_activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.google_fit.fetchSensorData.GoogleSensorDataImplementation
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import kotlinx.coroutines.launch

class ActivityViewModel(private val googleFetchUseCase: GoogleFetchUseCase) : ViewModel() {
    var steps by mutableStateOf(0)
    var goalSteps by mutableStateOf(10000)
    var newGoal by mutableStateOf("")

    fun subscribeToStepsListener(context: Context, activity: Activity) {
        googleFetchUseCase.getNecessaryParameters(activity = activity, context = context)
        if (!googleFetchUseCase.isUserSubscribed()) {
            googleFetchUseCase.subscribeToStepsListener()
        } else {
            Log.i(TAG, "User already subscribed to STEP_COUNT_DELTA")
        }
    }

    fun listDataSources(context: Context, activity: Activity) {
        val googleSensorDataImplementation = GoogleSensorDataImplementation()
        googleSensorDataImplementation.getActivityAndContext(activity = activity, context = context)
        googleSensorDataImplementation.listAvailableDataSources()
        googleSensorDataImplementation.addRawDataListener()
    }

    fun updateStepCount() {
        viewModelScope.launch {
            steps = googleFetchUseCase.fetchCurrentSteps()
        }
    }

    fun calculatePersentageOfGoal(): Float {
        return steps.toFloat() / goalSteps.toFloat()
    }

    fun setNewGoalSteps() {
        if (newGoal != "") {
            if (newGoal.toInt() != 0) {
                goalSteps = newGoal.toInt()
            }
        }
    }
}