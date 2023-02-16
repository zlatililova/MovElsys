package com.example.movelsys.presentation_layer.activity_tracking.life_activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.google_fit.GoogleFitPermissions
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import kotlinx.coroutines.launch

class ActivityViewModel(private val googleFetchUseCase: GoogleFetchUseCase) : ViewModel() {
    var steps: Int = 0
    var weeklySteps: Int = 0
    var monthlySteps: Int = 0
    private var areWeeklyAndMonthlyStepsFetched = 0
    var goalSteps by mutableStateOf(0)
    lateinit var activity: Activity


    fun fetchLastSavedSteps() {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val defaultValue = 0
        if (sharedPref != null) {
            goalSteps = sharedPref.getInt("newGoalSteps", defaultValue)
        }
        Log.i("Steps", goalSteps.toString())
        fetchWeeklyAndMonthlySteps()
        areWeeklyAndMonthlyStepsFetched += 1
    }

    fun subscribeToStepsListener(context: Context, activity: Activity) {
        googleFetchUseCase.getNecessaryParameters(activity = activity, context = context)
        if (!googleFetchUseCase.isUserSubscribed()) {
            googleFetchUseCase.subscribeToStepsListener()
        } else {
            Log.i(TAG, "User already subscribed to STEP_COUNT_DELTA")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GoogleFitPermissions(
                appContext = context,
                activity = activity
            ).detectIfPermissionIsGiven()
        }
    }

    fun updateStepCount() {
        viewModelScope.launch {
            steps = googleFetchUseCase.fetchCurrentSteps()
        }
    }

    private fun fetchWeeklyAndMonthlySteps() {
        viewModelScope.launch {
            monthlySteps = googleFetchUseCase.fetchMonthlySteps(areWeeklyAndMonthlyStepsFetched)
            weeklySteps = googleFetchUseCase.fetchWeeklySteps()
        }
    }

    fun calculatePercentageOfGoal(steps: Int, goalSteps: Int): Float {
        return steps.toFloat() / goalSteps.toFloat()
    }
}