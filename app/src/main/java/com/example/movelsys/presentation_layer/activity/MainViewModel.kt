package com.example.movelsys.presentation_layer.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.movelsys.data_layer.google_fit.GoogleFetchData
import com.example.movelsys.data_layer.google_fit.GoogleFitPermissions
import com.example.movelsys.data_layer.google_fit.Responses
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import com.google.android.gms.fitness.data.DataPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val googleFetchUseCase: GoogleFetchUseCase
): ViewModel() {

    var googleFitHistory = listOf<DataPoint>()

    fun startGoogleFit(context: Context, activity: Activity){
        googleFetchUseCase.getNecessaryParameters(activity, context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GlobalScope.launch(Dispatchers.Main) {
                GoogleFitPermissions(
                    appContext = context,
                    activity = activity
                ).detectIfPermissionIsGiven()
                googleFetchUseCase.startBussinessLogic(responses = object: Responses {
                    override fun onSuccess(dataSet: MutableList<DataPoint>) {
                        Log.i(TAG, "OnSuccess")
                    }
                    override fun onError(error: String) {
                        Log.i(TAG, "OnError")
                    }
                })

            }
            googleFitHistory = googleFetchUseCase.getDataPoints()
        }
    }


}