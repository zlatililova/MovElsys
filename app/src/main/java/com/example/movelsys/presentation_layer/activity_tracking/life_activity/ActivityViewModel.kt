package com.example.movelsys.presentation_layer.activity_tracking.life_activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import kotlinx.coroutines.launch

class ActivityViewModel(private val googleFetchUseCase: GoogleFetchUseCase) : ViewModel() {
    var steps: Int = 0
    var weeklySteps: Int = 0
    var monthlySteps: Int = 0
    private var timesWeeklyAndMonthlyStepsWereFetched = 0
    var goalSteps by mutableStateOf(10000)
    lateinit var activity: Activity


    fun fetchLastSavedSteps() {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val defaultValue = 10000
        if (sharedPref != null) {
            goalSteps = sharedPref.getInt("newGoalSteps", defaultValue)
        }
        Log.i("Steps", goalSteps.toString())
        if ((timesWeeklyAndMonthlyStepsWereFetched <= 1 && monthlySteps == 0) || weeklySteps == 0) {
            fetchWeeklyAndMonthlySteps()
            timesWeeklyAndMonthlyStepsWereFetched += 1
        }

    }

    fun subscribeToStepsListener(context: Context, activity: Activity) {
        googleFetchUseCase.getNecessaryParameters(activity = activity, context = context)
        if (!googleFetchUseCase.isUserSubscribed()) {
            googleFetchUseCase.subscribeToStepsListener()
        } else {
            Log.i(TAG, "User already subscribed to STEP_COUNT_DELTA")
        }
        googleFetchUseCase.detectGivenPermissions(activity, context)
    }

    fun updateStepCount() {
        viewModelScope.launch {
            steps = googleFetchUseCase.fetchCurrentSteps()
        }
    }

    private fun fetchWeeklyAndMonthlySteps() {
        viewModelScope.launch {
            monthlySteps = googleFetchUseCase.fetchMonthlySteps()
            weeklySteps = googleFetchUseCase.fetchWeeklySteps()
            timesWeeklyAndMonthlyStepsWereFetched -= 1
        }
    }

    fun calculatePercentageOfGoal(steps: Int, goalSteps: Int): Pair<Int, Float> {
        var percentage = steps.toFloat() / goalSteps.toFloat()
        var index = 0
        while (percentage > 1f) {
            percentage -= 1f
            index += 1
            if (index >= 2) {
                index = 0
            }
        }
        return Pair(index, percentage)
    }
}