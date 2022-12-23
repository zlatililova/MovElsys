package com.example.movelsys.data_layer.google_fit

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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


    private val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1
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
                // Use response data here
                Log.i(ContentValues.TAG, "OnSuccess()\n AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa")
            }
            .addOnFailureListener { e -> Log.d(ContentValues.TAG, "OnFailure()", e) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun detectIfPermissionIsGiven(){
        val MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 1

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                activity, // your activity
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
                account,
                fitnessOptions)
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION)
            }
            Log.i(ContentValues.TAG, "NOT GIVEN NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
        } else {
            Log.i(ContentValues.TAG, "PERMISSION IS GIVEN YEEEEEEEEEEEEEEEES")
            accessGoogleFit()
        }
        Log.i(ContentValues.TAG, GoogleSignIn.hasPermissions(account, fitnessOptions).toString())
    }
}