package com.example.movelsys.presentation_layer.activity_tracking.life_activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.movelsys.data_layer.google_fit.GoogleSensorDataImplementation
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase

class ActivityViewModel(private val googleFetchUseCase: GoogleFetchUseCase) {

    fun subscribeToStepsListener(context: Context, activity: Activity){
        googleFetchUseCase.getNecessaryParameters(activity = activity, context = context)
        if(!googleFetchUseCase.isUserSubscribed()) {
           googleFetchUseCase.subscribeToStepsListener()
       }else{
           Log.i(TAG, "User already subscribed to STEP_COUNT_DELTA")
       }
    }

    fun listDataSources(context: Context, activity: Activity){
        val googleSensorDataImplementation = GoogleSensorDataImplementation()
        googleSensorDataImplementation.getActivityAndContext(activity = activity, context = context)
        googleSensorDataImplementation.listAvailableDataSources()
        googleSensorDataImplementation.addRawDataListener()
    }
    fun UpdateStepCount() {
        /*val mainHandler = Handler(Looper.getMainLooper())
        val googleSensorDataImplementation = GoogleSensorDataImplementation()
        val updateTextTask = object : Runnable {
            override fun run() {
                Log.i(TAG, googleSensorDataImplementation.getAddStepValue().toString())

                mainHandler.postDelayed(this, 1000)
            }
        }
        mainHandler.post(updateTextTask)*/
    }

}