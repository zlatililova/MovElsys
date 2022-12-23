package com.example.movelsys.data_layer.google_fit

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class GoogleFitPermissions(
    private val appContext: Context,
    private val activity: Activity
) {
    private val googleFitPermissionsRequestCode = 1
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()
    private val account = GoogleSignIn.getAccountForExtension(appContext, fitnessOptions)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun accessGoogleFit() {
        val end = LocalDateTime.now()
        val start = end.minusYears(1)
        val endSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond()
        val startSeconds = start.atZone(ZoneId.systemDefault()).toEpochSecond()
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .setTimeRange(startSeconds, endSeconds, TimeUnit.SECONDS)
            .bucketByTime(1, TimeUnit.DAYS)
            .build()
        val account = GoogleSignIn.getAccountForExtension(appContext, fitnessOptions)
        Fitness.getHistoryClient(activity, account)
            .readData(readRequest)
            .addOnSuccessListener {
                Log.i(
                    ContentValues.TAG, "OnSuccess()"
                )
            }
            .addOnFailureListener { e -> Log.d(ContentValues.TAG, "OnFailure()", e) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun detectIfPermissionIsGiven() {

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                activity, // your activity
                googleFitPermissionsRequestCode, // e.g. 1
                account,
                fitnessOptions
            )
            Log.i(ContentValues.TAG, "Permission not previously given")
        } else {
            Log.i(ContentValues.TAG, "Permission is given")
            accessGoogleFit()
        }
    }


}