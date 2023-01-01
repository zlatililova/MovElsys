package com.example.movelsys.presentation_layer.activity

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
import com.example.movelsys.data_layer.google_fit.Responses
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val googleFetchUseCase: GoogleFetchUseCase
): ViewModel() {

    var googleFitHistory: Map<String, Int> by mutableStateOf(mapOf())

    fun startGoogleFit(context: Context, activity: Activity){
        googleFetchUseCase.getNecessaryParameters(activity, context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            viewModelScope.launch {
                GoogleFitPermissions(
                    appContext = context,
                    activity = activity
                ).detectIfPermissionIsGiven()
                googleFetchUseCase.startBusinessLogic(responses = object: Responses {
                    override fun onSuccess() {
                        Log.i(TAG, "OnSuccess")
                    }
                    override fun onError(error: String) {
                        Log.i(TAG, "OnError")
                    }
                })
            delay(1500)
                fetchGoogleData()
            }
        }
    }


    fun fetchGoogleData(){
        val gson = Gson()
        googleFitHistory = gson.fromJson(googleFetchUseCase.getDataPoints(), object : TypeToken<Map<String, Int>>() {}.type)
        googleFitHistory.forEach{
            Log.i(TAG, it.key + ": " + it.value)
        }
    }
}